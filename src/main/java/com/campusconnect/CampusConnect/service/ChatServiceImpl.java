package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.DtoHelperClass;
import com.campusconnect.CampusConnect.entity.ChatEntity;
import com.campusconnect.CampusConnect.entity.MessageEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.ChatRepository;
import com.campusconnect.CampusConnect.repositories.MessageRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private final DtoHelperClass dtoHelperClass = new DtoHelperClass();

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
        return chatRepository.findById(chatId).map(chat -> {
            message.setSentAt(new Date());
            messageRepository.save(message);

            // Add the message to the chat
            chat.getMessages().offer(message);
            chat.setLastUpdated(new Date());
            return chatRepository.save(chat);
        });
    }

    @Override
    public Optional<List<ChatEntity>> getAllChats(ObjectId userId) {
         Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
             UserEntity user = userOptional.get();
            List<ObjectId> chatIds = new ArrayList<>(user.getAllChats().keySet());
            List<ChatEntity> chats = chatRepository.findAllById(chatIds);
            return Optional.of(chats);
        } else {
            return Optional.empty();
        }
    }

}
