package com.campusconnect.CampusConnect.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Messages")
@Data
public class MessageEntity {

    @Id
    private ObjectId id;

    @NotNull(message = "Chat ID cannot be null")
    private ObjectId chatId; // References the associated ChatEntity.

    @NotNull(message = "Sender ID cannot be null")
    private ObjectId senderId; // References the sender UserEntity.

    @NotNull(message = "Message content cannot be null")
    private String content;

    private Date sentAt = new Date(); // When the message was sent.

    private boolean isRead = false; // Flag to indicate if the message was read.

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                ", isRead=" + isRead +
                '}';
    }
}
