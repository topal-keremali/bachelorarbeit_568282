package com.htwberlin.azebe.service;

import com.htwberlin.azebe.TestVariables;
import com.htwberlin.azebe.repository.ShiftRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:mysql://localhost:3306/arbeitszeiterfassung_test"})
public class ShiftServiceIT {

    @Autowired
    ShiftService shiftService;
    @Autowired
    ShiftRepository shiftRepository;

    @BeforeEach
    void beforeEach() {
        shiftRepository.deleteAll();
    }

    @Test
    public void test_get_all_shift() {
        Optional<String> begin = Optional.empty();
        Optional<String> ende = Optional.empty();
        assertEquals(1, shiftService.getAll(2, begin, ende, TestVariables.validEmployeeJwt).size());
    }
}
