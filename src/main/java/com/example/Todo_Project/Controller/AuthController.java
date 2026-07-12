package com.example.Todo_Project.Controller;

import com.example.Todo_Project.Models.User;
import com.example.Todo_Project.Repository.UserRepository;
import com.example.Todo_Project.Service.UserService;
import com.example.Todo_Project.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
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
        String url = "https://raw.githubusercontent.com/Sakthivel1107/image-storage/main/images/defaultImage.png";
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
        return ResponseEntity.ok(Map.of("token",token));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserById() throws Exception{
        return new ResponseEntity<>(userService.findUserById(userService.loggedInUserId()),HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody Map<String ,String> body) throws Exception{
        String name = body.get("name");
        User user = userService.findUserById(userService.loggedInUserId());
        user.setName(name);
        userService.updateUser(user);
        return ResponseEntity.ok("Name updated successfully");
    }
}
