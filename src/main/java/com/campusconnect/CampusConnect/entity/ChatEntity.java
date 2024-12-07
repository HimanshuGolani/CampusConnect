package com.campusconnect.CampusConnect.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "Chats")
@Data
public class ChatEntity {

    @Id
    private ObjectId id;

    private ObjectId senderId;
    private ObjectId receiverId;

    private String senderName;
    private String receiverName;

    private Date createdAt = new Date();
    private Date lastUpdated = new Date();

    @DBRef
    private Queue<MessageEntity> messages = new PriorityQueue<>(Comparator.comparing(MessageEntity::getSentAt).reversed());

}
