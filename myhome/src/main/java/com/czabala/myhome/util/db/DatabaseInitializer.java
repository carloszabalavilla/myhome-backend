package com.czabala.myhome.util.db;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.sql.Date;

/** Creates only fictional users in the explicitly enabled, disposable demo. */
@Component
@Profile("demo")
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository users;

    public DatabaseInitializer(UserRepository users) {
        this.users = users;
    }

    @Override
    public void run(String... args) {
        if (users.findByEmail("demo@example.invalid").isPresent()) return;
        User user = new User();
        user.setEmail("demo@example.invalid");
        user.setPassword("DemoOnly123!");
        user.setFirstName("Demo");
        user.setLastName("MyHome");
        user.setBirthDate(Date.valueOf("2000-01-01"));
        user.initializeUser();
        user.setConfirmed(true);
        users.save(user);
    }
}
