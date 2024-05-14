package com.czabala.myhome.controller;

import com.czabala.myhome.controller.TaskController;
import com.czabala.myhome.domain.model.dao.FamilyGroup;
import com.czabala.myhome.domain.model.dao.Task;
import com.czabala.myhome.domain.model.dao.User;
import com.czabala.myhome.domain.model.dto.TaskDTO;
import com.czabala.myhome.service.database.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTasksReturnsAllTasks() {
        Set<Task> tasks = new HashSet<>();
        when(taskService.findAll()).thenReturn(tasks);

        var response = taskController.findAllTasks();

        assertEquals(tasks, response.getBody());
    }

    @Test
    public void findTaskByIdReturnsTask() {
        Task task = new Task();
        when(taskService.findById(1L)).thenReturn(task);

        var response = taskController.findTaskById(1L);

        assertEquals(task, response.getBody());
    }

    @Test
    public void findTaskByUserReturnsTasks() {
        User user = new User();
        Set<Task> tasks = new HashSet<>();
        when(taskService.findByUser(user)).thenReturn(tasks);

        var response = taskController.findTaskByUser(user);

        assertEquals(tasks, response.getBody());
    }

    @Test
    public void findTaskByFamilyGroupReturnsTasks() {
        FamilyGroup familyGroup = new FamilyGroup();
        Set<Task> tasks = new HashSet<>();
        when(taskService.findByFamilyGroup(familyGroup)).thenReturn(tasks);

        var response = taskController.findTaskByFamilyGroup(familyGroup);

        assertEquals(tasks, response.getBody());
    }

    @Test
    public void createTaskReturnsCreatedTask() {
        TaskDTO taskDTO = new TaskDTO();
        Task task = new Task();
        when(taskService.add(taskDTO)).thenReturn(task);

        var response = taskController.createTask(taskDTO);

        assertEquals(task, response.getBody());
    }

    @Test
    public void updateTaskReturnsUpdatedTask() {
        TaskDTO taskDTO = new TaskDTO();
        Task task = new Task();
        when(taskService.update(taskDTO)).thenReturn(task);

        var response = taskController.updateTask(taskDTO);

        assertEquals(task, response.getBody());
    }

    @Test
    public void deleteTaskDeletesTask() {
        taskController.deleteTask(1L);
    }
}