package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.*;
import com.campusconnect.CampusConnect.entity.CompanyEntity;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.CompanyRepository;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final CompanyRepository companyRepository;
    private final DtoHelperClass dtoHelper;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UniversityService.class);

    public UniversityService(UniversityRepository universityRepository, CompanyRepository companyRepository, UserRepository userRepository, DtoHelperClass dtoHelper) {
        this.universityRepository = universityRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.dtoHelper = dtoHelper;
    }

    public List<UniversityNameListDTO> getAllUniversities() {
        logger.info("Fetching all universities.");
        return universityRepository.findAllNamesOfUniversity();
    }

    public List<UserDTO> findAllStudents(ObjectId universityId) {
        logger.info("Fetching all students for university ID: {}", universityId);
        return universityRepository.findById(universityId)
                .map(university -> university.getAllStudents().stream()
                        .map(dtoHelper::mapToUserDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public CompanyDTO createCompany(ObjectId universityId, CompanyDTO companyDetails) {
        logger.info("Creating company for university ID: {} with company name: {}", universityId, companyDetails.getCompanyName());
        Optional<UniversityEntity> universityEntityOptional = universityRepository.findById(universityId);
        if (universityEntityOptional.isPresent()) {
            UniversityEntity university = universityEntityOptional.get();
            companyDetails.setUniversityId(universityId);
            CompanyEntity companyEntity1 = dtoHelper.mapToCompanyEntity(companyDetails);
            companyEntity1.setUniversityId(universityId);
            CompanyEntity savedEntity = companyRepository.save(companyEntity1);
            university.getCompanyList().add(savedEntity);
            companyDetails.setId(savedEntity.getId());
            universityRepository.save(university);
            logger.info("Company created successfully for university ID: {}", universityId);
            return companyDetails;
        }
        logger.error("University not found with ID: {}", universityId);
        return null;
    }

    public List<CompanyDTO> findAllCompaniesVisiting(ObjectId universityId) {
        logger.info("Fetching all companies visiting for university ID: {}", universityId);
        return universityRepository.findById(universityId)
                .map(universityEntity -> universityEntity.getCompanyList()
                        .stream()
                        .map(dtoHelper::mapToCompanyDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public void addStudentToCompany(ObjectId userId, ObjectId universityId, ObjectId companyId) {
        logger.info("Adding student with user ID: {} to company ID: {} for university ID: {}", userId, companyId, universityId);
        // Fetch the company
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> {
                    logger.error("Company with ID {} not found.", companyId);
                    return new IllegalArgumentException("Company with ID " + companyId + " not found");
                });

        // Verify the company belongs to the university
        if (!company.getUniversityId().equals(universityId)) {
            logger.error("Company with ID {} is not associated with university ID {}", companyId, universityId);
            throw new IllegalStateException("Company with ID " + companyId + " is not associated with university ID " + universityId);
        }

        // Fetch the user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User with ID {} not found.", userId);
                    return new IllegalArgumentException("User with ID " + userId + " not found");
                });

        // Check if the user is already added
        if (company.getSelectedStudents().stream().anyMatch(student -> student.getId().equals(user.getId()))) {
            logger.error("User with ID {} is already added to company with ID {}", userId, companyId);
            throw new IllegalStateException("User with ID " + userId + " is already added to the company");
        }

        // Add user to the company's selected students
        company.getSelectedStudents().add(user);
        companyRepository.save(company);
        logger.info("User with ID {} added to company with ID {} successfully.", userId, companyId);
    }

    public UniversityProfileDto getUniversityProfile(ObjectId universityId) {
        logger.info("Fetching profile for university ID: {}", universityId);
        UniversityEntity university = universityRepository.findById(universityId)
                .orElseThrow(() -> {
                    logger.error("University not found with ID: {}", universityId);
                    return new IllegalArgumentException("University not found");
                });

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

    public UserDTO findUserByEmail(String email, ObjectId universityId) {
        logger.info("Searching for user with email: {} in university ID: {}", email, universityId);
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailInUniversity(email, universityId);
        if (userEntityOptional.isPresent()) {
            logger.info("User with email: {} found in university ID: {}", email, universityId);
            return dtoHelper.mapToUserDTO(userEntityOptional.get());
        } else {
            logger.error("User with email: {} not found in university ID: {}", email, universityId);
            return null;
        }
    }
}
