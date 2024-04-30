package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.TaskDTO;
import com.czabala.myhome.service.database.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

public class TaskController {
    private final TaskService taskService;
    private final String endpoint = "/task";

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(endpoint)
    public ResponseEntity<?> findAllTasks() {
        Set<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping(endpoint + "/id")
    public ResponseEntity<?> findTaskById(@RequestParam(value = "id") long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping(endpoint + "/user")
    public ResponseEntity<?> findTaskByUser(@RequestParam(value = "user") User user) {
        Set<Task> tasks = taskService.findByUser(user);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(endpoint)
    public ResponseEntity<?> createTask(@RequestParam(value = "task") TaskDTO taskDTO) {
        Task newTask = taskService.add(taskDTO);
        return ResponseEntity.ok(newTask);
    }

    @PutMapping(endpoint)
    public ResponseEntity<?> updateTask(@RequestParam(value = "task") TaskDTO taskDTO) {
        Task updatedTask = taskService.update(taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping(endpoint)
    public ResponseEntity<String> deleteTask(@RequestParam(value = "id") long id) {
        taskService.delete(id);
        return ResponseEntity.ok("Tarea eliminada exitosamente");
    }

}
