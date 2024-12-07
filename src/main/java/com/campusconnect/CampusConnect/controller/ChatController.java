package com.campusconnect.CampusConnect.controller;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messaging")
public class ChatController {

    @PostMapping("/chat")
    public ResponseEntity<?> createOrGetChat(@RequestBody ObjectId senderId , @RequestBody ObjectId receiverId){
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<?> addMessage(){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
