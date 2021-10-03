package com.htwberlin.azebe.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.htwberlin.azebe.exception.UnauthorizedException;
import com.htwberlin.azebe.model.Role;
import com.htwberlin.azebe.model.Shift;
import com.htwberlin.azebe.model.User;
import com.htwberlin.azebe.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.htwberlin.azebe.security.SecurityConstants.SECRET;
import static com.htwberlin.azebe.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class ShiftService {

    private ShiftRepository shiftRepository;

    private UserService userService;

    private EmailService emailService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    public ShiftService(ShiftRepository shiftRepository, UserService userService, EmailService emailService) {
        this.shiftRepository = shiftRepository;
        this.userService = userService;
        this.emailService = emailService;
    }


    public Shift startShift(int id) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        var today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        var todayMidnight = LocalDateTime.of(today, midnight);
        var user = userService.getUserById(id);
        Optional<Shift> shift = shiftRepository.findShiftToday(user.getId(), todayMidnight, LocalDateTime.now());

        if (shift.isEmpty()) {
            var startShift = new Shift(LocalDateTime.now(), user);
            shiftRepository.save(startShift);
            emailService.sendBegin(user, startShift);
            return startShift;
        } else {
            return null;
        }
    }

    public Shift endShift(int id) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        var now = LocalDateTime.now();
        var today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        var todayMidnight = LocalDateTime.of(today, midnight);
        var user = userService.getUserById(id);
        Optional<Shift> shift = shiftRepository.findShiftToday(user.getId(), todayMidnight, LocalDateTime.now());

        if (shift.isEmpty()) {
            return null;
        } else {
            var endShift = shift.get();
            var checkLock = endShift.getBegin();
            if (now.isAfter(checkLock.plusMinutes(5))) {
                endShift.setEnd(LocalDateTime.now());
                endShift.setWorktime(this.calculateWorktime(endShift));
                shiftRepository.save(endShift);
                emailService.sendEnd(user, endShift);
                return endShift;
            }
            return null;
        }
    }

    public List<Shift> getAll(int id, Optional<String> begin, Optional<String> end, String token) {
        //parse date
        LocalDate beginDate;
        LocalDate endDate;
        if (begin.isEmpty()) {
            beginDate = LocalDate.of(1970, 1, 1);
        } else {
            if (begin.get().length() < 10) {
                beginDate = LocalDate.of(1970, 1, 1);
            } else {
                beginDate = LocalDate.parse(begin.get(), formatter);
            }
        }

        if (end.isEmpty()) {
            endDate = LocalDate.of(2100, 1, 1);
        } else {
            if (end.get().length() < 10) {
                endDate = LocalDate.of(2100, 1, 1);
            } else {
                endDate = LocalDate.parse(end.get(), formatter);
            }
        }

        // parse the token.
        int idFromToken = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getClaim("id").asInt();
        User user = userService.getUserById(idFromToken);
        if (idFromToken == user.getId()) {
            return shiftRepository.getAllById(id, Date.valueOf(beginDate), Date.valueOf(endDate));
        } else if (user.getRole() == Role.EMPLOYER) {
            return shiftRepository.getAllById(id, Date.valueOf(beginDate), Date.valueOf(endDate));
        } else throw new UnauthorizedException("Unauthorized");
    }

    private String calculateWorktime(Shift shift) {
        int duration = (int) ChronoUnit.MINUTES.between(shift.getBegin(), shift.getEnd());
        int hours = duration / 60;
        int minutes = duration % 60;

        return String.format("%d:%02d", hours, minutes);
    }

}
