package com.campusconnect.CampusConnect.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "files")
@Getter
@Setter
public class File {
    @Id
    @Generated
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String fileType;
    private String content;

    public  File(String name, String fileType , String content){
        this.name=name;
        this.fileType=fileType;
        this.content=content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(name, file.name) && Objects.equals(fileType, file.fileType) && Objects.equals(content, file.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fileType, content);
    }
}
