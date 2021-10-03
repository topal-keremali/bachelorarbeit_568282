package com.htwberlin.azebe.controller;

import com.htwberlin.azebe.model.Shift;
import com.htwberlin.azebe.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/shift")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping("/get/all/")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER','ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public List<Shift> getAllShiftsByUserId(@RequestParam int id, @RequestParam Optional<String> begin, @RequestParam Optional<String> end, @RequestHeader(name = "Authorization") String token) {
        return shiftService.getAll(id, begin, end, token);
    }

    @PutMapping("/begin")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER','ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> beginShift(@RequestParam int id) {
        shiftService.startShift(id);
        return Collections.singletonMap("success", true);
    }

    @PutMapping("/end")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER','ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> endShift(@RequestParam int id) {
        shiftService.endShift(id);
        return Collections.singletonMap("success", true);
    }
}
