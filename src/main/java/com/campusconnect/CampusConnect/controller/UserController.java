package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.dto.UserProfileDto;
import com.campusconnect.CampusConnect.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/myPosts/{userId}")
    public ResponseEntity<?> getAllUsersPosts(@PathVariable ObjectId userId){
        try{
            List<PostDTO> usersPost = userService.getAllPosts(userId);
            return new ResponseEntity<>(usersPost,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/myProfile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable ObjectId userId){
        try{
            UserProfileDto userDTO = userService.getUserProfile(userId);
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
    }



}


