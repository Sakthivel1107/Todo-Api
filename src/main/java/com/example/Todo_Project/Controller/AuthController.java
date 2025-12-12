package com.example.Todo_Project.Controller;

import com.example.Todo_Project.JwtFilter;
import com.example.Todo_Project.Models.User;
import com.example.Todo_Project.Models.UserImage;
import com.example.Todo_Project.Repository.UserRepository;
import com.example.Todo_Project.Service.UserService;
import com.example.Todo_Project.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "https://sakthivel1107.github.io/todoui")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String ,String> body){
        String name = body.get("name");
        String email = body.get("email");
        String password = passwordEncoder.encode(body.get("password"));
        String url = "http://localhost:8080/uploads/defaultImage.png";
        if(userRepository.findByEmail(email).isPresent())
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        userService.createUser(User.builder().name(name).email(email).password(password).url(url).build());
        return new ResponseEntity<>("Registered Successfully",HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User body){
        String email = body.getEmail();
        String password = body.getPassword();
        var userObject = userRepository.findByEmail(email);
        if(userObject.isEmpty()){
            return new ResponseEntity<>("User not registered",HttpStatus.UNAUTHORIZED);
        }
        User user = userObject.get();
        if(!passwordEncoder.matches(password,user.getPassword()))
            return new ResponseEntity<>("Credentials are not valid!",HttpStatus.UNAUTHORIZED);
        String token = jwtUtil.generateKey(email);
        return ResponseEntity.ok(Map.of("token",token,"userid",user.getId()));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByid(@PathVariable long id){
        return new ResponseEntity<>(userService.findUserById(id),HttpStatus.OK);
    }

    private static final String UPLOAD_DIR = "uploads/";
    @PutMapping
    public ResponseEntity<?> updateUserData(@ModelAttribute UserImage user) throws IOException {
        if(user.getImage().isEmpty()){
            return new ResponseEntity<>("Please select a file",HttpStatus.BAD_REQUEST);
        }
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        String fileName = user.getImage().getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        String prevUrl = user.getUrl();
        if(!prevUrl.equals("http://localhost:8080/uploads/defaultImage.png")){
            String[] prevUrlParts = prevUrl.split("/");
            String prevImgName = prevUrlParts[prevUrlParts.length-1];
            Path prevImagePath = Paths.get("uploads/"+ prevImgName);
            Files.deleteIfExists(prevImagePath);
        }
        Files.copy(user.getImage().getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        String fileUrl = "http://localhost:8080/uploads/"+fileName;
        User userUrl = new User();
        userUrl.setId(user.getId());
        userUrl.setEmail(user.getEmail());
        userUrl.setPassword(user.getPassword());
        userUrl.setName(user.getName());
        userUrl.setUrl(fileUrl);
        return new ResponseEntity<>(userService.updateUser(userUrl),HttpStatus.OK);
    }
}
