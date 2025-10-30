package com.example.Todo_Project.Repository;

import com.example.Todo_Project.Models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findAllTodoByUserId(Long userId);
}
