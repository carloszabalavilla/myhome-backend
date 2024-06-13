package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.util.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(long id);

    Iterable<User> findByRole(Role role);

    Optional<User> findByEmail(String email);

}
