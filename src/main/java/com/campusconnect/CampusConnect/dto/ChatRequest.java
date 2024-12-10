package com.campusconnect.CampusConnect.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class ChatRequest {
    private ObjectId senderId;
    private ObjectId receiverId;
}
