package com.example.Todo_Project.Service;

import com.example.Todo_Project.Models.User;
import com.example.Todo_Project.Repository.UserRepository;
import com.example.Todo_Project.config.GitHubConfig;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private GitHubConfig config;
    public void createUser(User user){
        usersRepository.save(user);
    }
    public String uploadFile( MultipartFile file) {
        try {
            String filenameExtension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String key = UUID.randomUUID().toString() + "." + filenameExtension;
            String base64Content = Base64.getEncoder().encodeToString(file.getBytes());
            String apiUrl = "https://api.github.com/repos/"+config.getUsername()+"/"+config.getRepo()+"/contents/images/image"+key;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization","token "+ config.getToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String,String> body  = new HashMap<>();
            body.put("message","upload image");
            body.put("content",base64Content);
            body.put("branch", config.getBranch());
            HttpEntity<Map<String,String>> request = new HttpEntity<>(body,headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(apiUrl,request);
            return "https://raw.githubusercontent.com/"+config.getUsername()+"/"+config.getRepo()+"/"+config.getBranch()+"/images/image"+key;
        }
        catch (Exception e){
            throw new RuntimeException("Image upload failed",e);
        }
    }
    public void deleteFileByName(String fileName) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            fileName = fileName.substring(fileName.lastIndexOf('/')+1);
            String filePath = "images/"+fileName;
            String apiUrl = "https://api.github.com/repos/"+config.getUsername()+"/"+config.getRepo()+"/contents/"+filePath;
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization","token "+config.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );
            String sha = (String)response.getBody().get("sha");
            String deleteBody = """
                    {
                    "message": "delete image %s",
                    "sha": "%s",
                    "branch": "%s"
                    }
                    """.formatted(fileName,sha, config.getBranch());
            HttpHeaders deleteHeaders = new HttpHeaders();
            deleteHeaders.set("Authorization","token "+config.getToken());
            deleteHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> deleteRequest = new HttpEntity<>(deleteBody, deleteHeaders);
            restTemplate.exchange(
                    apiUrl,
                    HttpMethod.DELETE,
                    deleteRequest,
                    String.class
            );
        }
        catch (HttpClientErrorException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public User findUserById(String id) throws Exception{
        return usersRepository.findById(id).orElseThrow(()->new RuntimeException("User Not found"));
    }
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }
    public User updateUser(User user){
         return usersRepository.save(user);
    }
    public String loggedInUserId() throws Exception{
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User user= usersRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found with this email"));
        return user.getId();
    }
}
