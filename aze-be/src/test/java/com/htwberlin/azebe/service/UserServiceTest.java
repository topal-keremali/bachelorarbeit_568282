package com.htwberlin.azebe.service;

import com.htwberlin.azebe.TestVariables;
import com.htwberlin.azebe.exception.UserNotExistentException;
import com.htwberlin.azebe.model.User;
import com.htwberlin.azebe.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void beforeEach() {
        userService = new UserService(userRepository);
    }

    @Test
    public void test_get_user_by_id() {
        Optional<User> userOptional = Optional.of(TestVariables.one);
        when(userRepository.findById(anyInt())).thenReturn(userOptional);

        User actualUser = userService.getUserById(1);

        assertEquals(TestVariables.one.getName(), actualUser.getName());
    }

    @Test
    public void test_get_user_by_id_throws_exception() {
        Optional<User> userOptional = Optional.empty();
        when(userRepository.findById(anyInt())).thenReturn(userOptional);

        assertThrows(UserNotExistentException.class, () -> userService.getUserById(1));
    }
}
