package com.htwberlin.azebe.service;

import com.htwberlin.azebe.TestVariables;
import com.htwberlin.azebe.model.Shift;
import com.htwberlin.azebe.repository.ShiftRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ShiftServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private ShiftRepository shiftRepository;
    @Mock
    private EmailService emailService;


    @BeforeEach
    public void beforeEach() {
        shiftService = new ShiftService(shiftRepository, userService, emailService);
    }

    @InjectMocks
    private ShiftService shiftService;

    @Test
    public void test_creating_new_shift_for_today() {
        Optional<Shift> shiftOptional = Optional.empty();
        when(userService.getUserById(anyInt())).thenReturn(TestVariables.one);
        when(shiftRepository.findShiftToday(any(Integer.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(shiftOptional);
        Shift shift = shiftService.startShift(1);
        assertEquals(null, shift.getEnd());
    }

    @Test
    public void test_shift_block() {
        Optional<Shift> shiftOptional = Optional.of(TestVariables.blockedShift);
        when(userService.getUserById(anyInt())).thenReturn(TestVariables.one);
        when(shiftRepository.findShiftToday(any(Integer.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(shiftOptional);
        Shift shift = shiftService.endShift(1);
        assertEquals(null, shift);
    }


    @Test
    public void test_get_all_shift() {
        Optional<String> begin = Optional.empty();
        Optional<String> ende = Optional.empty();

        when(shiftRepository.getAllById(anyInt(), any(Date.class), any(Date.class))).thenReturn(TestVariables.shiftList);
        when(userService.getUserById(anyInt())).thenReturn(TestVariables.employer);
        assertEquals(3, shiftService.getAll(2, begin, ende, TestVariables.validEmployeeJwt).size());
    }
}
