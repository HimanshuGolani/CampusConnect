package com.campusconnect.CampusConnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
@NoArgsConstructor
public class UniversityProfileDto {

    private ObjectId universityId;
    private String universityName;
    private String locationOfUniversity;
    private int nirfRank;
    private int numberOfStudents;
    private String officerHead;
    private Date establishedIn;
    private int numberOfCompaniesVisiting;

    public UniversityProfileDto(ObjectId universityId, String locationOfUniversity, String universityName, int nirfRank, int numberOfStudents, String officerHead, Date establishedIn, int numberOfCompaniesVisiting) {
        this.universityId = universityId;
        this.locationOfUniversity = locationOfUniversity;
        this.universityName = universityName;
        this.nirfRank = nirfRank;
        this.numberOfStudents = numberOfStudents;
        this.officerHead = officerHead;
        this.establishedIn = establishedIn;
        this.numberOfCompaniesVisiting = numberOfCompaniesVisiting;
    }
}
