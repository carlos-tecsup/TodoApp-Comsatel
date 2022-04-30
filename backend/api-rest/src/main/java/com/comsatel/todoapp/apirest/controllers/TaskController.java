package com.comsatel.todoapp.apirest.controllers;

import com.comsatel.todoapp.apirest.models.Task;
import com.comsatel.todoapp.apirest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value = "/api")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> findAll() {
        return taskService.findAllTasks();
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> create(@Valid @RequestBody Task task, BindingResult result) {
        Task taskNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            taskNew = taskService.saveTask(task);
        } catch (DataAccessException e) {
            response.put("message", "Could not create task");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Task created successfully");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Task task, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            taskService.updateTask(id, task);
        } catch (DataAccessException e) {
            response.put("message", "Could not update task");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Task update successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/tasks/state/{id}")
    public ResponseEntity<?> updateState(@Valid @RequestBody Task task, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            taskService.updateStateTask(id, task);
        } catch (DataAccessException e) {
            response.put("message", "Could not update state of task");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "State of task update successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            taskService.deleteTaskById(id);
        } catch (DataAccessException e) {
            response.put("message", "Failed to delete task");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Deleted task successfully");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
