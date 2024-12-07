package com.campusconnect.CampusConnect.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection =  "Company")
@Data
public class CompanyEntity {

    @Id
    private ObjectId id;

    @NotNull(message = "Company name cannot be null")
    @Indexed
    private String companyName;

    private ObjectId universityId;

    @DBRef
    private List<UserEntity> selectedStudents = new ArrayList<>();


}
