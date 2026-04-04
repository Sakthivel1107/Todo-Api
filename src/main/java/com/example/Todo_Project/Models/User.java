package com.example.Todo_Project.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Users")
public class User {
    @Id
    String id;
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
