package com.czabala.myhome.util.db;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.util.security.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void loadData() {
        if (userRepository.count() <= 0) {
            User owner = getOwner();
            userRepository.save(owner);
        }
    }

    private User getOwner() {
        User owner = new User();
        owner.setEmail("czabaladev@gmail.com");
        owner.setPassword("Rukia1225dev!");
        owner.setFirstName("carlos");
        owner.setLastName("zabala");
        owner.setBirthDate(Date.valueOf("1998-03-20"));
        owner.initializeUser();
        owner.setRole(Role.ADMINISTRATOR);
        owner.setConfirmed(true);
        return owner;
    }
}