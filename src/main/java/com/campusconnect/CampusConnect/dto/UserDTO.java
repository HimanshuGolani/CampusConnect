package com.campusconnect.CampusConnect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO implements CommonDTO {
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "User name cannot be null")
    private String userName;

    @NotNull(message = "University name cannot be null")
    private String nameOfUniversity;

    private long universityReg;

    private String course;

    private String branch;

    private String currentCompany;

    private String placementStatement;

}
