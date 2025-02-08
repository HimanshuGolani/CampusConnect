package com.campusconnect.CampusConnect.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "folders")
@Getter
@Setter
public class Folder {
    @Id
    @Generated
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private ObjectId id;
    private String name;
    @DBRef
    private List<Folder> subFolders = new ArrayList<>();
    @DBRef
    private List<File> files = new ArrayList<>();
    private List<String> links =  new ArrayList<>();
    public Folder(String name){
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.id) && Objects.equals(name, folder.name) && Objects.equals(subFolders, folder.subFolders) && Objects.equals(files, folder.files) && Objects.equals(links, folder.links);
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subFolders=" + subFolders +
                ", files=" + files +
                ", links=" + links +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, subFolders, files, links);
    }
}
