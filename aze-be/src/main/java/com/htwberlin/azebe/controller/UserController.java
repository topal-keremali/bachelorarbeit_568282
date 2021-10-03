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

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/get/id")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@RequestParam int id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public void putUser(@RequestBody UserDto userDto) {
        var user = convertToEntity(userDto);
        userService.updateUser(user);
    }

    @GetMapping("/get/name")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByName(@RequestParam String name) {
        return userService.getUserByName(name);
    }

    @DeleteMapping("/delete/id")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@RequestParam int id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping("/delete/name")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByName(@RequestParam String name) {
        userService.deleteUserByName(name);
    }

    @PostMapping("/sign-up")
    @PreAuthorize("hasAnyRole('ROLE_IT_ADMIN','ROLE_EMPLOYER')")
    public void signUp(@RequestBody UserDto userDto) {
        var user = convertToEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserList() {
        return userService.getAllUser();
    }


    public User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

}
