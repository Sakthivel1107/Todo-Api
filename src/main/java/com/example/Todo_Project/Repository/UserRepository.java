package com.example.Todo_Project.Repository;

import com.example.Todo_Project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
