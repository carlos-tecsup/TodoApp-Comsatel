package com.comsatel.todoapp.apirest.repositories;

import com.comsatel.todoapp.apirest.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task,Long> {
}
