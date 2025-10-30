package com.example.Todo_Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class hello {
    public static void main(String[] args)throws IOException {
        String prevUrl = "https://localhost:8080/uploads/img1.jpg";
        String[] prevUrlParts = prevUrl.split("[/]");
        String prevImgName = prevUrlParts[prevUrlParts.length-1];
        Path prevImagePath = Paths.get("uploads/" + prevImgName);
        System.out.println(prevImagePath);
        Files.deleteIfExists(prevImagePath);
        if(!false)
            System.out.println("successful");
    }
}
