package com.htwberlin.azebe;

import com.htwberlin.azebe.controller.ShiftController;
import com.htwberlin.azebe.controller.UserController;
import com.htwberlin.azebe.repository.ShiftRepository;
import com.htwberlin.azebe.repository.UserRepository;
import com.htwberlin.azebe.service.ShiftService;
import com.htwberlin.azebe.service.UserService;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AzeBeApplicationTests {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShiftController shiftController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        assertThat(bCryptPasswordEncoder).isNotNull();
        assertThat(modelMapper).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(shiftService).isNotNull();
        assertThat(shiftRepository).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(shiftController).isNotNull();
        assertThat(userController).isNotNull();
    }

}
