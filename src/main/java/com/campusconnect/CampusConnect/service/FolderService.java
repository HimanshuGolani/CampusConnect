package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.entity.File;
import com.campusconnect.CampusConnect.entity.Folder;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.exception.UniversityNotFoundException;
import com.campusconnect.CampusConnect.repositories.FileRepository;
import com.campusconnect.CampusConnect.repositories.FolderRepository;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final UniversityRepository universityRepository;

    public FolderService(FolderRepository folderRepository,UniversityRepository universityRepository){
        this.folderRepository=folderRepository;
        this.universityRepository=universityRepository;
    }

    private UniversityEntity findUniversity(ObjectId universityId){
        return universityRepository.findById(universityId)
                .orElseThrow( () -> new UniversityNotFoundException("The university not found during the folder service process."));
    }

    @Transactional
    public Folder createFolder(ObjectId universityId , String name){
        UniversityEntity university = findUniversity(universityId);
        Folder folder = folderRepository.save(new Folder(name));
        university.getUseFullLinks().add(folderRepository.save(folder));
        universityRepository.save(university);
        return folder;
    }

    public List<Folder> getFolderById(ObjectId id){
        UniversityEntity university = findUniversity(id);
        return university.getUseFullLinks();
    }

    @Transactional
    public Folder addSubFolder(ObjectId universityId ,ObjectId parentId, String subFolderName){
       try{
           UniversityEntity university = findUniversity(universityId);
           Optional<Folder> parentFolderOpt = folderRepository.findById(parentId);
           if (parentFolderOpt.isPresent()) {
               Folder parentFolder = parentFolderOpt.get();
               Folder subFolder = new Folder(subFolderName);
               parentFolder.getSubFolders().add(folderRepository.save(subFolder));
               folderRepository.save(parentFolder);
               universityRepository.save(university);
               return parentFolder;
           }
       }
       catch (Exception e){
           System.out.println(e.getMessage());
       }
        return null;
    }

    public Folder addFileToFolder(ObjectId universityId ,ObjectId folderId , File file){
        UniversityEntity university = findUniversity(universityId);
        Optional<Folder> folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isPresent()) {
            Folder folder = folderOptional.get();
            folder.getFiles().add(file);
            folderRepository.save(folder);
            university.getUseFullLinks().add(folder);
            universityRepository.save(university);
            return folder;
        }
        return null;
    }

}
