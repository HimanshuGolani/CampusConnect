package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.entity.MessageEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<MessageEntity, ObjectId> {}
