package com.campusconnect.CampusConnect.chat.service;



import com.campusconnect.CampusConnect.chat.entity.ChatMessage;
import com.campusconnect.CampusConnect.chat.repository.ChatMessageRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(ObjectId chatId) {
        return chatMessageRepository.findByChatId(chatId);
    }

    public long countNewMessages(ObjectId chatId, ObjectId recipientId) {
        return chatMessageRepository.countByChatIdAndRecipientIdAndReadFalse(chatId, recipientId);
    }
}
