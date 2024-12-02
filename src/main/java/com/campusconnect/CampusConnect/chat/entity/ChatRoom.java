package com.campusconnect.CampusConnect.chat.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ChatRooms")
public class ChatRoom {
    @Id
    private ObjectId id;
    private ObjectId senderId;
    private ObjectId recipientId;
}
