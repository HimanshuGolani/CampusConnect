package com.campusconnect.CampusConnect.chat.service;

import com.campusconnect.CampusConnect.chat.entity.ChatRoom;
import com.campusconnect.CampusConnect.chat.repository.ChatRoomRepository;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<ObjectId> getChatId(ObjectId senderId, ObjectId recipientId, boolean createIfNotExist) {
        Optional<UserEntity> sender = userRepository.findById(senderId);
        Optional<UserEntity> recipient = userRepository.findById(recipientId);

        if (sender.isPresent() && recipient.isPresent()) {
            if (sender.get().getAllChats().containsKey(recipientId)) {
                return Optional.of(sender.get().getAllChats().get(recipientId));
            }

            if (createIfNotExist) {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setSenderId(senderId);
                chatRoom.setRecipientId(recipientId);
                chatRoom = chatRoomRepository.save(chatRoom);

                sender.get().getAllChats().put(recipientId, chatRoom.getId());
                recipient.get().getAllChats().put(senderId, chatRoom.getId());

                userRepository.save(sender.get());
                userRepository.save(recipient.get());

                return Optional.of(chatRoom.getId());
            }
        }

        return Optional.empty();
    }
}
