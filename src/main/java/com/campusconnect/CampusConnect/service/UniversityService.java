package com.campusconnect.CampusConnect.service;
import com.campusconnect.CampusConnect.dto.*;
import com.campusconnect.CampusConnect.entity.CompanyEntity;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.CompanyRepository;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UniversityService  {

    private final UniversityRepository universityRepository;
    private final CompanyRepository companyRepository;
    private final DtoHelperClass dtoHelper;

    private final UserRepository userRepository;

    public UniversityService(UniversityRepository universityRepository,CompanyRepository companyRepository,UserRepository userRepository,DtoHelperClass dtoHelper){
        this.universityRepository = universityRepository;
        this.companyRepository=companyRepository;
        this.userRepository=userRepository;
        this.dtoHelper=dtoHelper;
    }

    public List<UniversityNameListDTO> getAllUniversities() {
        return universityRepository.findAllNamesOfUniversity();
    }

    public List<UserDTO> findAllStudents(ObjectId universityId) {
        return universityRepository.findById(universityId)
                .map(university -> university.getAllStudents().stream()
                        .map(dtoHelper::mapToUserDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public CompanyDTO createCompany(ObjectId universityId , CompanyDTO companyDetails){
        Optional<UniversityEntity> universityEntityOptional = universityRepository.findById(universityId);
        if (universityEntityOptional.isPresent()){
            UniversityEntity university = universityEntityOptional.get();
            companyDetails.setUniversityId(universityId);
            CompanyEntity companyEntity1 = dtoHelper.mapToCompanyEntity(companyDetails);
            companyEntity1.setUniversityId(universityId);
            CompanyEntity savedEntity = companyRepository.save(companyEntity1);
            university.getCompanyList().add(savedEntity);
            companyDetails.setId(savedEntity.getId());
            universityRepository.save(university);
            return companyDetails;
        }
        return null;
    }

    public List<CompanyDTO> findAllCompaniesVisiting(ObjectId universityId){
        return universityRepository.findById(universityId)
                .map(universityEntity -> universityEntity.getCompanyList()
                        .stream()
                        .map(dtoHelper::mapToCompanyDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public void addStudentToCompany(List<ObjectId> userIds, ObjectId universityId, ObjectId companyId) {
        universityRepository.findById(universityId).flatMap(university -> companyRepository.findById(companyId)).ifPresent(company -> {
            List<UserEntity> selectedStudents = company.getSelectedStudents();
            List<UserEntity> usersToAdd = userRepository.findAllById(userIds);

            // Add only the users not already in the selectedStudents list
            Set<ObjectId> selectedStudentIds = selectedStudents.stream()
                    .map(UserEntity::getId)
                    .collect(Collectors.toSet());

            usersToAdd.stream()
                    .filter(user -> !selectedStudentIds.contains(user.getId()))
                    .forEach(selectedStudents::add);

            companyRepository.save(company); // Save updated company with new students
        });
    }

    public UniversityProfileDto getUniversityProfile(ObjectId universityId){
        UniversityEntity university = universityRepository.findById(universityId)
                .orElseThrow(()-> new IllegalArgumentException("University not found"));
        return new UniversityProfileDto(
                university.getUniversityId(),
                university.getLocationOfUniversity(),
                university.getNameOfUniversity(),
                university.getNirfRanking(),
                university.getAllStudents().size(),
                university.getOfficerHead(),
                university.getEstablishedIn(),
                university.getCompanyList().size()
        );
    }


}
