package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.dto.UniversityNameListDTO;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends MongoRepository<UniversityEntity, ObjectId> {
    Optional<UniversityEntity> findByEmail(String email);

    @Query(value = "{}", fields = "{ '_id' : 1 , 'nameOfUniversity' : 1 }")
    List<UniversityNameListDTO> findAllNamesOfUniversity();

}
