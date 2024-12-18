package com.campusconnect.CampusConnect.dto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UserLoginDto {

    private String userName;

    // Use Jackson's @JsonSerialize annotation to ensure it's serialized as a string
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;

    private boolean loginStatus;

    private String token;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId universityId;

    public UserLoginDto() {
    }

    public UserLoginDto(String userName, ObjectId id,ObjectId universityId) {
        this.userName = userName;
        this.id = id;
        this.universityId=universityId;
    }
}
