package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.entity.ChatEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<ChatEntity, ObjectId> {}
