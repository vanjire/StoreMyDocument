package com.store.store_my_documents.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.store_my_documents.Dtos.LoginDto;
import com.store.store_my_documents.Dtos.RegisterDto;
import com.store.store_my_documents.entity.User;
import com.store.store_my_documents.exceptions.UserAlreadyExistsException;
import com.store.store_my_documents.repository.UserRepo;
import com.store.store_my_documents.service.JwtService;
import com.store.store_my_documents.service.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsersService userService;
	
    private final JwtService jwtService;
    public AuthController(AuthenticationManager authenticationManager,UsersService userService,JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService=userService;
        this.jwtService= jwtService;
    }

   
    
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterDto dto) {
    	
        userService.saveUser(dto);
        
        return ResponseEntity
                .status(201)
                .body("User registered successfully");
    }
    
}
