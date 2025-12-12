package com.example.Todo_Project.Controller;

import com.example.Todo_Project.Models.Todo;
import com.example.Todo_Project.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@CrossOrigin(origins = "https://sakthivel1107.github.io/todoui")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<Todo>> getAllTodos(@PathVariable Long userId) {
        return new ResponseEntity<>(todoService.getTodos(userId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Todo> insertTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.insertTodo(todo),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Long id){
        todoService.deleteTodo(id);
    }
    @PutMapping
    public ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
    }
}
