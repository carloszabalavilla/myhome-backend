package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.dao.FamilyGroup;
import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.TaskDTO;
import com.czabala.myhome.domain.repository.TaskRepository;
import com.czabala.myhome.util.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
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
            throw new EntityNotFoundException("No hay tareas registradas");
        }
        return tasks;
    }

    @Override
    public Task findById(long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new EntityNotFoundException("Tarea no encontrada.");
        }
        return task;
    }

    @Override
    public Set<Task> findByUser(User user) {
        Set<Task> tasks = taskRepository.findByUser(user);
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("No hay tareas registradas");
        }
        return tasks;
    }

    //TODO: Implement this method
    @Override
    public Set<Task> findByFamilyGroup(FamilyGroup familyGroup) {
        return Set.of();
    }

    @Override
    public Task add(TaskDTO taskDTO) {
        return taskRepository.save(new ModelMapper().map(taskDTO, Task.class));
    }

    @Override
    public Task update(TaskDTO taskDTO) {
        if ((findById(taskDTO.getId())) == null) {
            throw new EntityNotFoundException("Tarea no encontrada");
        }
        Task task = new ModelMapper().map(taskDTO, Task.class);
        return taskRepository.save(task);
    }

    @Override
    public void delete(long id) {
        findById(id);
        taskRepository.deleteById(id);
    }
}
