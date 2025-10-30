package com.example.Todo_Project.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue
    Long id;
    Long userId;
    @NotNull
    @NotEmpty
    String title;
    boolean isCompleted;
}
