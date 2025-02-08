package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.entity.File;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, ObjectId> {}
