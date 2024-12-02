package com.campusconnect.CampusConnect.chat.repository;


 import com.campusconnect.CampusConnect.chat.entity.ChatRoom;
 import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, ObjectId> {
}
