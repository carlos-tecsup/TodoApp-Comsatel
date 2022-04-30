package com.comsatel.todoapp.apirest.services;

import com.comsatel.todoapp.apirest.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    public List<Task> findAllTasks();

    public Task saveTask(Task task);

    public Task updateTask(Long id, Task task);

    public void deleteTaskById(Long id);

    public Task updateStateTask(Long id, Task task);


}
