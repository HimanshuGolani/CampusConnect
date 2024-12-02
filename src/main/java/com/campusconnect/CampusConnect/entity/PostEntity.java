package com.campusconnect.CampusConnect.entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.Set;

@Document(collection = "Posts")
@Data
public class PostEntity {

    @Id
    private ObjectId id;

    @Indexed
    @NotNull
    private ObjectId usersId;

    @NotNull
    private String userName;

    private String email;

    @NotNull
    private ObjectId universityId;

    private String title;
    private String content;

    private String imageUri;


     private COMPANY_NAME_TAG companySpecificName_TAG;

    private Set<COMPANY_NAME_TAG> companySpecifiName_TAGS_List;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

}
