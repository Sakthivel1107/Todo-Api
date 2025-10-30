package com.example.Todo_Project.Service;

import com.example.Todo_Project.Models.Todo;
import com.example.Todo_Project.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    public List<Todo> getTodos(Long userId){
        return todoRepository.findAllTodoByUserId(userId);
    }
    public Todo insertTodo(Todo todo){
        return todoRepository.save(todo);
    }
    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }
    public Todo updateTodo(Todo todo){
        return todoRepository.save(todo);
    }
}
