package com.htwberlin.azebe.controller;

import com.htwberlin.azebe.dto.UserDto;
import com.htwberlin.azebe.model.User;
import com.htwberlin.azebe.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  User controller.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    @GetMapping("/get/id")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@RequestParam int id) {
        return userService.getUserById(id);
    }

    /**
     * Put user.
     *
     * @param userDto the user dto
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public void putUser(@RequestBody UserDto userDto) {
        var user = convertToEntity(userDto);
        userService.updateUser(user);
    }

    /**
     * Gets user by name.
     *
     * @param name the name
     * @return the user by name
     */
    @GetMapping("/get/name")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByName(@RequestParam String name) {
        return userService.getUserByName(name);
    }

    /**
     * Delete user by id.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/id")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@RequestParam int id) {
        userService.deleteUserById(id);
    }

    /**
     * Delete user by name.
     *
     * @param name the name
     */
    @DeleteMapping("/delete/name")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByName(@RequestParam String name) {
        userService.deleteUserByName(name);
    }

    /**
     * Sign up.
     *
     * @param userDto the user dto
     */
    @PostMapping("/sign-up")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    public void signUp(@RequestBody UserDto userDto) {
        var user = convertToEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
    }

    /**
     * Gets user list.
     *
     * @return the user list
     */
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserList() {
        return userService.getAllUser();
    }


    /**
     * Convert to entity user.
     *
     * @param userDto the user dto
     * @return the user
     */
    public User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

}
