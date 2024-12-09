package com.campusconnect.CampusConnect.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class UserProfileDto {

    private String userName;
    private String email;
    private ObjectId universityId;
    private String universityName;
    private String course;
    private String branch;
    private String placementStatement;
    private String currentCompany;

    public UserProfileDto(String email, String userName, ObjectId universityId, String universityName, String course, String branch, String placementStatement, String currentCompany) {
        this.email = email;
        this.userName = userName;
        this.universityId = universityId;
        this.universityName = universityName;
        this.course = course;
        this.branch = branch;
        this.placementStatement = placementStatement;
        this.currentCompany = currentCompany;
    }
}

