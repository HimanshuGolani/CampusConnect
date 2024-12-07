package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.entity.ChatEntity;
import com.campusconnect.CampusConnect.entity.MessageEntity;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface ChatService {

    Optional<ChatEntity> createOrGetChat(ObjectId senderId, ObjectId receiverId);

    Optional<ChatEntity> addMessage(ObjectId chatId, MessageEntity message);
}
