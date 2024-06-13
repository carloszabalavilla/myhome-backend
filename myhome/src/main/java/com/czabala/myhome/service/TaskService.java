package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.Family;
import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.TaskDTO;

import java.util.Set;

public interface TaskService extends Service<Task> {

    Set<Task> findByUser(long id);

    Set<Task> findByFamily(Family family);


}
