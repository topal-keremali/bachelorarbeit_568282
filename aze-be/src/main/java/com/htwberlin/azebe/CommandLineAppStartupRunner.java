package com.htwberlin.azebe;

import com.htwberlin.azebe.model.Role;
import com.htwberlin.azebe.model.User;
import com.htwberlin.azebe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${setup.admin.password}")
    private String password;

    @Override
    public void run(String... args) {
        try {
            var admin = new User("Admin", Role.IT_ADMIN, "admin", password);
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
            userRepository.save(admin);
        } catch (Exception e) {
            logger.info("Admin user already in the db");
        }
    }
}
