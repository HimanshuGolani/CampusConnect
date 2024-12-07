package com.campusconnect.CampusConnect.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UniversityLoginDto {
    private String universityName;
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;
    private boolean Login_Status;

    public  UniversityLoginDto() {

    }

    public UniversityLoginDto(ObjectId id , String universityName){
        this.id=id;
        this.universityName=universityName;
    }
}
