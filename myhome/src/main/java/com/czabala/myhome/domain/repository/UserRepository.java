package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.enums.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAll();

    User findById(long id);
    Set<User> findByRole(UserRole role);

    User findByEmail(String email);

    User findByConfirmationToken(String token);
}
