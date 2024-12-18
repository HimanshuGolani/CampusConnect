package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.dto.*;
import com.campusconnect.CampusConnect.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // User signup
    @PostMapping("/user/signup")
    public ResponseEntity<?> userSignUp(@Valid @RequestBody UserDTO userData) {
        try{
            authService.userSignUp(userData);
            return new ResponseEntity<>(userData.getUserName() , HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User login
    @PostMapping("/user/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody LoginDTO userData) {
        UserLoginDto data = authService.userLogin(userData);
        if (data.isLoginStatus()) {
            return ResponseEntity.status(200).body(data);
        } else {
            return ResponseEntity.status(401).body(data);
        }
    }

    // University login
    @PostMapping("/university/login")
    public ResponseEntity<?> universityLogin(@Valid @RequestBody LoginDTO universityData) {
        UniversityLoginDto data = authService.universityLogin(universityData);
        if (data.isLogin_Status()) {
            return new ResponseEntity<>(data,HttpStatus.OK);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // University signup
    @PostMapping("/university/signup")
    public ResponseEntity<?> universitySignUp(@Valid @RequestBody UniversityDTO universityData) {
        try{
            authService.universitySignUp(universityData);
            return ResponseEntity.status(201).body("University successfully registered");
        }catch (Exception e){
            return ResponseEntity.status(501).body("Internal server error");
        }
    }
}

