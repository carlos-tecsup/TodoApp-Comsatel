package com.comsatel.todoapp.apirest.services;

import com.comsatel.todoapp.apirest.models.Task;
import com.comsatel.todoapp.apirest.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    @Transactional
    public Task saveTask(Task task) {
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task taskActual = taskRepository.findById(id).orElse(null);;
        taskActual.setDescription(task.getDescription());

        return taskRepository.save(taskActual);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task updateStateTask(Long id, Task task) {
        Task taskActual = taskRepository.findById(id).orElse(null);;
        taskActual.setCompleted(task.isCompleted());

        return taskRepository.save(taskActual);
    }


}
