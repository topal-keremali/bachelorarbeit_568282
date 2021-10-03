package com.htwberlin.azebe.service;

import com.htwberlin.azebe.exception.UserNotExistentException;
import com.htwberlin.azebe.model.Role;
import com.htwberlin.azebe.model.User;
import com.htwberlin.azebe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByName(String name) {
        userRepository.deleteUserByName(name);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("New user added with name: {} and role: {}", user.getName(), user.getRole());
    }

    public User getUserById(int id) {
        Optional<User> value = userRepository.findById(id);

        if (value.isPresent()) {
            return value.get();
        } else throw new UserNotExistentException("There is no user with id: " + id);
    }

    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var user = userRepository.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
        } catch (NullPointerException e) {
            throw new UserNotExistentException("User not found");
        }
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user.getRole() != Role.IT_ADMIN && user.getRole() != Role.EMPLOYER) {
                users.add(new User(user.getId(), user.getName()));
            }
        });
        return users;
    }

}
