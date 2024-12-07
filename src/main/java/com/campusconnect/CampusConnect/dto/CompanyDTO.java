package com.campusconnect.CampusConnect.dto;

import com.campusconnect.CampusConnect.entity.UserEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDTO {

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;

    @NotNull(message = "Company name cannot be empty")
    private String companyName;

    @NotNull(message = "university id cannot be empty")
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId universityId;

    private List<UserEntity> selectedStudents = new ArrayList<>();

}
