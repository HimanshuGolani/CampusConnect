package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.entity.ConfigCampusConnectEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigCampusConnectRepository extends MongoRepository<ConfigCampusConnectEntity, ObjectId> {

}
