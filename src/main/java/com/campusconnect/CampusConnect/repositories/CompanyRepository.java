package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.entity.CompanyEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<CompanyEntity, ObjectId> {}
