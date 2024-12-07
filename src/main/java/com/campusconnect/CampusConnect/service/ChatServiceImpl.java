package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.entity.ChatEntity;
import com.campusconnect.CampusConnect.entity.MessageEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.ChatRepository;
import com.campusconnect.CampusConnect.repositories.MessageRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository,MessageRepository messageRepository,UserRepository userRepository){
        this.chatRepository=chatRepository;
        this.messageRepository=messageRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Optional<ChatEntity> createOrGetChat(ObjectId senderId, ObjectId receiverId) {
        UserEntity sender = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        UserEntity receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("receiver not found"));
        if(sender.getAllChats().containsKey(receiverId) && receiver.getAllChats().containsKey(senderId)){
            ObjectId chatId = sender.getAllChats().get(receiverId);
            return chatRepository.findById(chatId);
        }
        else {
            ChatEntity chat = new ChatEntity();
            chat.setSenderId(senderId);
            chat.setReceiverId(receiverId);
            chat.setSenderName(sender.getUserName());
            chat.setReceiverName(receiver.getUserName());
            chat.setCreatedAt(new Date());
            chat.setLastUpdated(new Date());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChatEntity> addMessage(ObjectId chatId, MessageEntity message) {
        return Optional.empty();
    }
}
