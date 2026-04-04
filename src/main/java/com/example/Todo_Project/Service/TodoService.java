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
    @Autowired
    private UserService userService;
    public List<Todo> getTodos() throws Exception{
        try {
            return todoRepository.findAllTodoByUserId(userService.loggedInUserId());
        }
        catch (Exception e){
            throw new Exception("userId is invalid");
        }
    }
    public Todo insertTodo(Todo todo) throws Exception{
        try{
            todo.setUserId(userService.loggedInUserId());
            return todoRepository.save(todo);
        }
        catch (Exception e){
            throw new Exception("userId is invalid");
        }
    }
    public void deleteTodoById(String id) throws Exception{
        try{
            todoRepository.deleteById(id);
        }

        catch (Exception e){
            throw new Exception("userId is invalid");
        }
    }
    public Todo updateTodo(Todo todo) throws Exception{
        try{
            todo.setUserId(userService.loggedInUserId());
            return todoRepository.save(todo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
