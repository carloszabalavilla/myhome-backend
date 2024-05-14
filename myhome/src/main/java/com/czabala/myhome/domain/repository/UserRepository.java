package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.dao.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAll();

    User findById(long id);

    Set<User> findByRole(String role);

    User findByEmail(String email);

    User findByToken(String token);
}
