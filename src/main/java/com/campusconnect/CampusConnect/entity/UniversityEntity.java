package com.campusconnect.CampusConnect.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "University")
@Data
public class UniversityEntity {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid Email format")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    //  Name of the university
    @Indexed
    @NotNull(message = "university name cannot be empty")
    private String nameOfUniversity;

//  when the user selects the university make this field apply internally it self by the name to id
    @NotNull
    @DBRef
    private ObjectId universityId;

    //  placement officers head name
    private String officerHead;

    //  Collage was founded in
    private Date establishedIn;

    //  number of companies visit on an avg. year
    private int noOfCompanyVisit;

    //  ranking of the collage
    private int nirfRanking;

    //  location of the university
    private String locationOfUniversity;

    // list of students registered in that university name.
    @DBRef
    private List<UserEntity> allStudents = new ArrayList<>();

    //  List of the company's that comes to the campus for hiring.
    @DBRef(lazy = false)
    private Set<CompanyEntity> companyList =  new HashSet<>();


    @DBRef
    private List<PostEntity> universityRelatedPosts = new ArrayList<>();


}


