package com.example.Todo_Project.Models;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserImage {
    private long id;
    private String email;
    private String password;
    private String name;
    private MultipartFile image;
    private String url;
    UserImage(long id,String email,String password,String name,MultipartFile image,String url){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.image = image;
        this.url = url;
    }
}
