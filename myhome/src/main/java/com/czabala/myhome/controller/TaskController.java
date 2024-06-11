package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.Family;
import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.TaskDTO;
import com.czabala.myhome.service.TaskService;
import com.czabala.myhome.util.mapper.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * TaskController is a REST controller that handles task related requests.
 * It provides endpoints for finding all tasks, finding a task by id, user, or family group,
 * creating a task, updating a task, and deleting a task.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    /**
     * Constructs a new TaskController with the specified TaskService.
     *
     * @param taskService the TaskService to be used by the TaskController
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Handles a GET request to find all tasks.
     *
     * @return a ResponseEntity containing a Set of all Tasks
     */
    @GetMapping
    public ResponseEntity<Set<TaskDTO>> findAllTasks() {
        return ResponseEntity.ok(taskService.findAll());
    }

    /**
     * Handles a GET request to find a task by id.
     *
     * @param id the id of the task to find
     * @return a ResponseEntity containing the Task with the specified id
     */
    @GetMapping("/id")
    public ResponseEntity<TaskDTO> findTaskById(@RequestParam long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    /**
     * Handles a GET request to find tasks by user.
     *
     * @param user the user of the tasks to find
     * @return a ResponseEntity containing a Set of Tasks with the specified user
     */
    @GetMapping("/user")
    public ResponseEntity<Set<TaskDTO>> findTaskByUser(@RequestBody User user) {
        return ResponseEntity.ok(taskService.findByUser(user));
    }

    /**
     * Handles a GET request to find tasks by family group.
     *
     * @param family the family group of the tasks to find
     * @return a ResponseEntity containing a Set of Tasks with the specified family group
     */
    @GetMapping("/family-group")
    public ResponseEntity<Set<TaskDTO>> findTaskByFamilyGroup(@RequestParam(value = "family") Family family) {
        return ResponseEntity.ok(taskService.findByFamily(family));
    }

    /**
     * Handles a POST request to create a task.
     *
     * @param taskDTO the TaskDTO containing the new task's information
     * @return a ResponseEntity containing the created Task
     */
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.add(taskDTO));
    }

    /**
     * Handles a PUT request to update a task.
     *
     * @param taskDTO the TaskDTO containing the updated task's information
     * @return a ResponseEntity containing the updated Task
     */
    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.update(taskDTO));
    }

    /**
     * Handles a DELETE request to delete a task.
     *
     * @param id the id of the task to delete
     * @return a ResponseEntity containing a message indicating that the task has been deleted
     */
    @DeleteMapping
    public ResponseEntity<String> deleteTask(@RequestParam(value = "id") long id) {
        taskService.delete(id);
        return JsonObject.jsonMsgResponse(200, "Tarea eliminada exitosamente");
    }
}