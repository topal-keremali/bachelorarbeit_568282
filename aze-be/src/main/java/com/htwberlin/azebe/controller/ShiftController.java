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

/**
 * Shift controller.
 */
@RestController
@RequestMapping("/shift")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    /**
     * Gets all shifts by user id.
     *
     * @param id    the id
     * @param begin the begin
     * @param end   the end
     * @param token the token
     * @return the all shifts by user id
     */
    @GetMapping("/get/all/")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER','ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public List<Shift> getAllShiftsByUserId(@RequestParam int id, @RequestParam Optional<String> begin, @RequestParam Optional<String> end, @RequestHeader(name = "Authorization") String token) {
        return shiftService.getAll(id, begin, end, token);
    }

    /**
     * Begin shift.
     *
     * @param id the id
     * @return the map
     */
    @PutMapping("/begin")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER','ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> beginShift(@RequestParam int id) {
        shiftService.startShift(id);
        return Collections.singletonMap("success", true);
    }

    /**
     * End shift.
     *
     * @param id the id
     * @return the map
     */
    @PutMapping("/end")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER','ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> endShift(@RequestParam int id) {
        shiftService.endShift(id);
        return Collections.singletonMap("success", true);
    }
}
