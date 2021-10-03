package com.htwberlin.azebe.service;

import com.htwberlin.azebe.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {

    @Autowired
    private UserService userService;


    @Test
    public void test_get_user_by_name() {
        User user = userService.getUserByName("Testperson1");
        assertEquals("Testperson1", user.getName());
    }

    @Test
    public void test_get_user_by_username() {
        UserDetails user = userService.loadUserByUsername("testperson1");
        assertEquals("testperson1", user.getUsername());
    }
}
