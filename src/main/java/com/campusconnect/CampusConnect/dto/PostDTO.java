package com.campusconnect.CampusConnect.dto;

import com.campusconnect.CampusConnect.entity.COMPANY_NAME_TAG;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;

    @NotNull
    private String title;
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId userId;

    private String userName;

    private String content;

    private String imageUri;

    private COMPANY_NAME_TAG companySpecificName_TAG;

    private Set<COMPANY_NAME_TAG> companySpecificName_TAGS_List;

    private Date createdAt;

    public PostDTO(ObjectId id, String title, ObjectId userId, String content, String userName, String imageUri, Set<COMPANY_NAME_TAG> companySpecificName_TAGS_List, COMPANY_NAME_TAG companySpecificName_TAG, Date createdAt){
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.userName = userName;
        this.imageUri = imageUri;
        this.companySpecificName_TAGS_List = companySpecificName_TAGS_List;
        this.companySpecificName_TAG = companySpecificName_TAG;
        this.createdAt = createdAt;
    }
    
    public PostDTO(ObjectId id, ObjectId usersId, String userName, String title, String content, String imageUri) {
        this.id = id;
        this.userId=usersId;
        this.userName=userName;
        this.title=title;
        this.content=content;
        this.imageUri=imageUri;
    }

}


/*
{
    "title":"",
    "userId":"",
    "userName":"",
    "content":"",
    "imageUri":""
}
* */