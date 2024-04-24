package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Set<Task> findAll();
    Task findById(long id);
    Set<Task> findByUser(User user);
}
