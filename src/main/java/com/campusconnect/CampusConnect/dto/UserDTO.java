package com.campusconnect.CampusConnect.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
 import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UserDTO implements CommonDTO {
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;

    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "User name cannot be null")
    private String userName;

    @NotNull(message = "University name cannot be null")
    private String nameOfUniversity;

    @NotNull
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId universityId;

    private long universityReg;

    private String course;

    private String branch;

    private String currentCompany;

    private String placementStatement;

    private String leetCodeUserName;


}
