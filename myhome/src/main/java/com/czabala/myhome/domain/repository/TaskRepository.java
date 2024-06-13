package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<Task> findById(long id);

    Iterable<Task> findByUser(User user);
}
