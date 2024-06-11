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
    public Set<TaskDTO> findAll() {
        return toTaskDTO(taskRepository.findAll());
    }

    @Override
    public TaskDTO findById(long id) {
        return taskRepository.findById(id).orElseThrow().toDTO();
    }

    @Override
    public Set<TaskDTO> findByUser(User user) {
        return toTaskDTO(taskRepository.findByUser(user));
    }

    @Override
    public Set<TaskDTO> findByFamily(Family family) {
        return family.getUsers().stream()
                .flatMap(user -> StreamSupport.stream(taskRepository.findByUser(user).spliterator(), false))
                .map(Task::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public TaskDTO add(TaskDTO taskDTO) {
        if (taskRepository.findById(taskDTO.getId()).isPresent()) {
            throw new ConflictInDatabaseException("Tarea ya creada");
        }
        return taskRepository.save(taskDTO.toTask()).toDTO();
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        return taskRepository.save(taskDTO.toTask()).toDTO();
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
}
