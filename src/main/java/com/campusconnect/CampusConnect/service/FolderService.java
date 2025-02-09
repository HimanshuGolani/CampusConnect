package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.entity.Folder;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.exception.UniversityNotFoundException;
import com.campusconnect.CampusConnect.repositories.FolderRepository;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final UniversityRepository universityRepository;

    public FolderService(FolderRepository folderRepository, UniversityRepository universityRepository) {
        this.folderRepository = folderRepository;
        this.universityRepository = universityRepository;
    }

    private UniversityEntity findUniversity(ObjectId universityId) {
        return universityRepository.findById(universityId)
                .orElseThrow(() -> new UniversityNotFoundException("University not found."));
    }

    @Transactional
    public Folder createFolder(ObjectId universityId, String name) {
        UniversityEntity university = findUniversity(universityId);
        Folder folder = new Folder(name);
        folder = folderRepository.save(folder);
        university.getUseFullLinks().add(folder);
        universityRepository.save(university);
        return folder;
    }

    public List<Folder> getFoldersByUniversityId(ObjectId universityId) {
        UniversityEntity university = findUniversity(universityId);
        return university.getUseFullLinks();
    }

    @Transactional
    public Folder addSubFolder(ObjectId universityId, ObjectId parentId, String subFolderName) {
        findUniversity(universityId); // Just to validate university exists
        Folder parentFolder = folderRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent folder not found"));

        Folder subFolder = new Folder(subFolderName);
        subFolder = folderRepository.save(subFolder);
        parentFolder.getSubFolders().add(subFolder);
        folderRepository.save(parentFolder);
        return subFolder;
    }

    @Transactional
    public Folder addLinkToFolder(ObjectId universityId, ObjectId folderId, String link) {
        findUniversity(universityId); // Validate university exists
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        folder.getLinks().add(link);
        return folderRepository.save(folder);
    }
}
