package com.example.Todo_Project.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "UserTable")
public class User {
    @Id
    @GeneratedValue
    long id;
    @NotBlank
    String name;
    @Email
    @NotBlank
    @NotNull
    String email;
    @NotNull
    @NotBlank
    String password;
    String url;
}
