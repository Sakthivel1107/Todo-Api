package com.example.Todo_Project.Controller;

import com.example.Todo_Project.Models.User;
import com.example.Todo_Project.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/updateUser")
@CrossOrigin("*")
public class UpdateUserController {
    @Autowired
    private UserService userService;
    @PutMapping
    public ResponseEntity<String> updateUserData(@RequestParam("file") MultipartFile file) throws Exception {
        try{
            User user = userService.findUserById(userService.loggedInUserId());
            String imageUrl = userService.uploadFile(file);
            if(!user.getUrl().equals("https://raw.githubusercontent.com/Sakthivel1107/image-storage/main/images/defaultImage.png"))
                userService.deleteFileByName(user.getUrl());
            user.setUrl(imageUrl);
            User u = userService.updateUser(user);
            return ResponseEntity.ok(u.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user");
        }
    }
}
