package com.comsatel.todoapp.apirest.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "no puede estar vacio")
    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private boolean completed;
}
