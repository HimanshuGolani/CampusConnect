package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.entity.File;
import com.campusconnect.CampusConnect.entity.Folder;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.service.FolderService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/folders")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService){
        this.folderService=folderService;
    }



    @PostMapping("/{universityId}/create")
    public ResponseEntity<?> createFolder(@Valid @PathVariable ObjectId universityId , @RequestBody String name){
        Folder folder = folderService.createFolder(universityId,name);
        return new ResponseEntity<>(folder,HttpStatus.CREATED);
    }

    @GetMapping("/{universityId}")
    public ResponseEntity<?> getFolderByUniversity(@Valid @PathVariable ObjectId universityId) {
        List<Folder> folders = folderService.getFoldersByUniversityId(universityId);
        return new ResponseEntity<>(folders,HttpStatus.OK);
    }

    @PostMapping("/{universityId}/{folderId}/add-subfolder")
    public ResponseEntity<?> addSubFolder(@Valid @PathVariable ObjectId universityId ,@Valid @PathVariable ObjectId folderId,@Valid @RequestBody String subFolderName) {
        System.out.println(universityId +  " "  +folderId + " " +subFolderName);
        Folder folder = folderService.addSubFolder(universityId,folderId, subFolderName);
        return new ResponseEntity<>(folder,HttpStatus.CREATED);
    }

    @PostMapping("/{universityId}/{folderId}/add-link")
    public ResponseEntity<?> addFile(@Valid @PathVariable ObjectId universityId ,@Valid @PathVariable ObjectId folderId,@RequestBody String link) {
        Folder folder = folderService.addLinkToFolder(universityId,folderId, link);
        return new ResponseEntity<>(folder,HttpStatus.CREATED);
    }


}
