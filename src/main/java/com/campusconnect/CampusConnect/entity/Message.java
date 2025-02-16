package com.campusconnect.CampusConnect.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
@Data
public class Message {
    @NotNull
    private final ObjectId senderId;
    @NotNull
    private final ObjectId receiverId;
    @NotNull
    private String message;

}
