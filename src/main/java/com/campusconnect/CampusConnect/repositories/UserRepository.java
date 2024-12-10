package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    Optional<UserEntity> findByEmail(String email);

    @Query("{'email': ?0, 'universityId': ?1}")
    Optional<UserEntity> findByEmailInUniversity(String email, ObjectId universityId);
}
