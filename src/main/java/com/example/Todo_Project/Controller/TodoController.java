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
@CrossOrigin("*")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() throws Exception{
        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Todo> insertTodo(@RequestBody Todo todo) throws Exception{
        return new ResponseEntity<>(todoService.insertTodo(todo),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable String id) throws Exception{
        todoService.deleteTodoById(id);
    }
    @PutMapping
    public ResponseEntity<?> updateTodoById(@RequestBody Todo todo) throws Exception{
        try{
            return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NO_CONTENT);
        }

    }
}
