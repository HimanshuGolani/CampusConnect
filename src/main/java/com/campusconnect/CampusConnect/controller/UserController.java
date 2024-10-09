package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserService userService;


    @PostMapping("/createUser")
    public void CreateUser(@RequestBody UserEntity userData){
        System.out.println(userData.toString());
        userService.createUser(userData);
    }

}

// Functions to implement
// 1.
// 2.
// 3.
// 4.
// 5.
// 6.
// 7.
// 8.
// 9.
// 10.
