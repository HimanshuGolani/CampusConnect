package com.campusconnect.CampusConnect.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class ChatNotification {
    private ObjectId messageId;
    private ObjectId senderId;
    private String senderName;
}
