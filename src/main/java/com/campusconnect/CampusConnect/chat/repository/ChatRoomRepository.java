package com.campusconnect.CampusConnect.chat.repository;

import com.campusconnect.CampusConnect.chat.entity.ChatRoom;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, ObjectId> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(ObjectId senderId, ObjectId recipientId);
}