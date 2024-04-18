package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dto.TaskDTO;

import java.util.Set;

public interface TaskService extends Service<Task, TaskDTO>{
    Set<Task> findByUserId(long userId);
}