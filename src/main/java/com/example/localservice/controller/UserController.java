package com.example.localservice.controller;

import com.example.localservice.model.Role;
import com.example.localservice.dto.UserRequestDTO;
import com.example.localservice.dto.UserResponseDTO;
import com.example.localservice.dto.LoginRequestDTO;
import com.example.localservice.model.User;
import com.example.localservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request){
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        });

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        
        // Single admin constraint
        String MASTER_ADMIN_EMAIL = "charanyadav863931@gmail.com";
        if (request.getEmail().equalsIgnoreCase(MASTER_ADMIN_EMAIL)) {
            user.setRole(Role.ADMIN);
        } else if (Role.ADMIN.equals(request.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the designated email can register as ADMIN");
        } else {
            user.setRole(request.getRole());
        }

        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PostMapping("/login")
    public UserResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return toDto(user);
    }

    private UserResponseDTO toDto(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
