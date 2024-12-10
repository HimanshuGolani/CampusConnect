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
    public void addStudentToCompany(ObjectId userId, ObjectId universityId, ObjectId companyId) {
        // Fetch the company
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company with ID " + companyId + " not found"));

        // Verify the company belongs to the university
        if (!company.getUniversityId().equals(universityId)) {
            throw new IllegalStateException("Company with ID " + companyId + " is not associated with university ID " + universityId);
        }

        // Fetch the user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        // Check if the user is already added
        if (company.getSelectedStudents().stream().anyMatch(student -> student.getId().equals(user.getId()))) {
            throw new IllegalStateException("User with ID " + userId + " is already added to the company");
        }

        // Add user to the company's selected students
        company.getSelectedStudents().add(user);
        companyRepository.save(company);
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

    public UserDTO findUserByEmail(String email,ObjectId universityId) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailInUniversity(email,universityId);
        return userEntityOptional.map(dtoHelper::mapToUserDTO).orElse(null);
    }



}
