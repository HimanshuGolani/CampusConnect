package com.campusconnect.CampusConnect.chat.repository;

import com.campusconnect.CampusConnect.chat.entity.ChatMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, ObjectId> {
    List<ChatMessage> findByChatId(ObjectId chatId);
    long countByChatIdAndRecipientIdAndReadFalse(ObjectId chatId, ObjectId recipientId);
}