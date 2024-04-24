package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.TaskDTO;
import com.czabala.myhome.domain.repository.TaskRepository;
import com.czabala.myhome.util.exception.TaskNotFoundException;
import com.czabala.myhome.util.user.MapperDTOtoDAO;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Set<Task> findAll() {
        Set<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No hay tareas registradas");
        }
        return tasks;
    }

    @Override
    public Task findById(long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Tarea no encontrada.");
        }
        return task;
    }

    @Override
    public Set<Task> findByUser(User user) {
        Set<Task> tasks = taskRepository.findByUser(user);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No hay tareas registradas");
        }
        return tasks;
    }

    @Override
    public Task add(TaskDTO taskDTO) {
        Task task = new Task();
        MapperDTOtoDAO.copyNonNullFields(taskDTO, task);
        return taskRepository.save(task);
    }

    @Override
    public Task update(TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getId());
        if (task == null) {
            throw new TaskNotFoundException("Tarea no encontrada.");
        }
        MapperDTOtoDAO.copyNonNullFields(taskDTO, task);
        return taskRepository.save(task);
    }

    @Override
    public void delete(long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Tarea no encontrada.");
        }
        taskRepository.delete(task);
    }
}
