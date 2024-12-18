package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.entity.ChatEntity;
import com.campusconnect.CampusConnect.entity.MessageEntity;
import com.campusconnect.CampusConnect.service.ChatService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messaging")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService){
        this.chatService=chatService;
    }

    @GetMapping("/chats/{userId}")
    public ResponseEntity<?> getAllChats(@Valid @PathVariable ObjectId userId){
        Optional<List<ChatEntity>> chats = chatService.getAllChats(userId);
        if(chats.isPresent()) {
            return new ResponseEntity<>(chats.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/chat/{senderId}/{receiverId}")
    public ResponseEntity<?> createOrGetChat(@Valid @PathVariable ObjectId senderId, @Valid @PathVariable ObjectId receiverId){
        Optional<ChatEntity> chat = chatService.createOrGetChat(senderId, receiverId);
        if(chat.isPresent()){
            return new ResponseEntity<>(chat.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Controller Method to add a message to a chat
    @PostMapping("/chat/{chatId}")
    public ResponseEntity<?> addMessage(@PathVariable("chatId") ObjectId chatId, @RequestBody MessageEntity message){
        return chatService.addMessage(chatId, message)
                .map(updatedChat -> new ResponseEntity<>(updatedChat, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
