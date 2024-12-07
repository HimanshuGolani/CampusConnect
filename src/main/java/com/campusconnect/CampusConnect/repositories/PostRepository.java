package com.campusconnect.CampusConnect.repositories;

import com.campusconnect.CampusConnect.entity.PostEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<PostEntity, ObjectId> {
    // Find posts by university
    List<PostEntity> findByUniversityId(ObjectId universityId);

    // Search posts by title (partial match)
    List<PostEntity> findByTitleContainingIgnoreCase(String title);

    // Search posts by content (description, partial match)
    List<PostEntity> findByContentContainingIgnoreCase(String content);


    // Search posts by tags (tag matching in the array)
    @Query("{ 'companySpecificNameTAG': { $regex: ?0, $options: 'i' } }")
    List<PostEntity> findByCompanySpecificNameTAG_ListContainingIgnoreCase(String tag);

    // Text search across multiple fields (title, content, etc.)
    @Query("{'$text': {'$search': ?0}}")
    List<PostEntity> searchByText(String searchText);




}

