package com.example.Todo_Project.Service;

import com.example.Todo_Project.Models.User;
import com.example.Todo_Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository usersRepository;
    public User createUser(User user){
        return usersRepository.save(user);
    }
    public User findUserById(Long id){
        return usersRepository.findById(id).orElseThrow(()->new RuntimeException("User Not found"));
    }
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }
    public User updateUser(User user){
        return usersRepository.save(user);
    }
}
