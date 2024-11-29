package com.campusconnect.CampusConnect.chat.service;

import com.campusconnect.CampusConnect.chat.entity.ChatRoom;
import com.campusconnect.CampusConnect.chat.repository.ChatRoomRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Optional<ObjectId> getChatId(
            ObjectId senderId, ObjectId recipientId, boolean createIfNotExist) {

        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (!createIfNotExist) {
                        return Optional.empty();
                    }

                    ChatRoom senderRecipient = ChatRoom.builder()
                            .chatId(new ObjectId()) // Auto-generate ObjectId for chatId
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    ChatRoom recipientSender = ChatRoom.builder()
                            .chatId(senderRecipient.getChatId()) // Use the same chatId
                            .senderId(recipientId)
                            .recipientId(senderId)
                            .build();

                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(senderRecipient.getChatId());
                });
    }
}
