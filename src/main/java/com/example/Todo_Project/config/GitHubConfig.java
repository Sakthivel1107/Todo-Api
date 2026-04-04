package com.example.Todo_Project.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class GitHubConfig {
    @Value("${github.username}")
    private String username;
    @Value("${github.repo}")
    private String repo;
    @Value("${github.branch}")
    private String branch;
    @Value("${github.token}")
    private String token;
}

