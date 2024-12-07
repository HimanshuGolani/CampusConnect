package com.campusconnect.CampusConnect.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UniversityDTO implements CommonDTO {


    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid Email format")
    private String email;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "university name cannot be empty")
    private String nameOfUniversity;


    private String officerHead;

    private Date establishedIn;

    private int noOfCompanyVisit;

    private int nirfRanking;

    private String locationOfUniversity;

    private List<ObjectId> allStudents;

    private List<ObjectId> companyList;

}
