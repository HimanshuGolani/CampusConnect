package com.campusconnect.CampusConnect.chat.repository;

import com.campusconnect.CampusConnect.chat.entity.ChatMessage;
import com.campusconnect.CampusConnect.chat.entity.MessageStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository
        extends MongoRepository<ChatMessage, ObjectId> {

    long countBySenderIdAndRecipientIdAndStatus(
            ObjectId senderId, ObjectId recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(ObjectId chatId);
}