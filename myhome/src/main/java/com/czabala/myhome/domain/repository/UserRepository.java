package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.enums.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAll();
    Set<User> findByRole(UserRole role);
    Set<User> findByEmail(String email);
}
