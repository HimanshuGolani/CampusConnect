package com.campusconnect.CampusConnect.chat.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ChatMessages")
public class ChatMessage {

    @Id
    private ObjectId id;
    private ObjectId chatId;
    private ObjectId senderId;
    private ObjectId recipientId;
    private String senderName;
    private String content;
    private boolean read;
}
