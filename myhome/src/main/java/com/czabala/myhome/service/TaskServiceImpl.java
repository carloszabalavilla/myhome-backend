package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.Family;
import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.TaskDTO;
import com.czabala.myhome.domain.repository.TaskRepository;
import com.czabala.myhome.domain.repository.UserRepository;
import com.czabala.myhome.util.exception.ConflictInDatabaseException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;
    UserService userService;
    UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Set<Task> findAll() {
        return toSetTask(taskRepository.findAll());
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public Set<Task> findByUser(long id) {
        User user = userRepository.findById(id).orElseThrow();
        return toSetTask(taskRepository.findByUser(user));
    }

    @Override
    public Set<Task> findByFamily(Family family) {
        return family.getUsers().stream()
                .flatMap(user -> StreamSupport.stream(taskRepository.findByUser(user).spliterator(), false))
                .collect(Collectors.toSet());
    }

    @Override
    public Task add(Task taskDTO) {
        if (taskRepository.findById(taskDTO.getId()).isPresent()) {
            throw new ConflictInDatabaseException("Tarea ya creada");
        }
        return taskRepository.save(taskDTO);
    }

    @Override
    public Task update(Task taskDTO) {
        return taskRepository.save(taskDTO);
    }

    @Override
    public void delete(long id) {
        taskRepository.deleteById(id);
    }

    private Set<TaskDTO> toTaskDTO(Iterable<Task> tasks) {
        return StreamSupport.stream(tasks.spliterator(), false)
                .map(Task::toDTO)
                .collect(Collectors.toSet());
    }

    private Set<Task> toSetTask(Iterable<Task> tasks) {
        return StreamSupport.stream(tasks.spliterator(), false)
                .collect(Collectors.toSet());
    }
}
