package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.*;
import com.campusconnect.CampusConnect.entity.CompanyEntity;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.enums.RedisKeys;
import com.campusconnect.CampusConnect.repositories.CompanyRepository;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import com.campusconnect.CampusConnect.exception.CompanyNotFoundException;
import com.campusconnect.CampusConnect.exception.UniversityNotFoundException;
import com.campusconnect.CampusConnect.exception.UserNotFoundException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final DtoHelperClass dtoHelper;

    @Autowired
    private RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(UniversityService.class);

    // Constants for repeated messages
    private static final String UNIVERSITY_NOT_FOUND = "University not found with ID: {}";
    private static final String COMPANY_NOT_FOUND = "Company with ID: {} not found.";
    private static final String USER_NOT_FOUND = "User with ID: {} not found.";
    private static final String USER_ALREADY_ADDED = "User with ID: {} is already added to company with ID: {}.";

    public UniversityService(UniversityRepository universityRepository, CompanyRepository companyRepository, UserRepository userRepository, DtoHelperClass dtoHelper) {
        this.universityRepository = universityRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.dtoHelper = dtoHelper;
    }


    public List<UniversityNameListDTO> getAllUniversities() {
        logger.info("Fetching all universities.");
        List<UniversityNameListDTO> cacheList = redisService.get(RedisKeys.UNIVERSITY_LIST.toString(),List.class);
        if(cacheList != null){
            return cacheList;
        }
        else {
            List<UniversityNameListDTO> universityList = universityRepository.findAllNamesOfUniversity();
            redisService.set(RedisKeys.UNIVERSITY_LIST.toString(),universityList,6L);
            return universityList;
        }
    }

    public List<UserDTO> findAllStudents(ObjectId universityId) {
        logger.info("Fetching all students for university ID: {}", universityId);
        List<UserDTO> chacheUsersList = redisService.get(RedisKeys.LIST_OF_STUDENTS_OF.toString()+universityId,List.class);
        if(chacheUsersList != null){
            return chacheUsersList;
        }
        else {
            List<UserDTO> userDTOS =  universityRepository.findById(universityId)
                    .map(university -> university.getAllStudents().stream()
                            .map(dtoHelper::mapToUserDTO)
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
           redisService.set(RedisKeys.LIST_OF_STUDENTS_OF.toString()+universityId,userDTOS,24L);
            return userDTOS;
        }
    }

    @Transactional
    public CompanyDTO createCompany(ObjectId universityId, CompanyDTO companyDetails) {
        logger.info("Creating company for university ID: {} with company name: {}", universityId, companyDetails.getCompanyName());
        Optional<UniversityEntity> universityEntityOptional = universityRepository.findById(universityId);
        if (universityEntityOptional.isPresent()) {
            UniversityEntity university = universityEntityOptional.get();
            companyDetails.setUniversityId(universityId);
            CompanyEntity companyEntity = dtoHelper.mapToCompanyEntity(companyDetails);
            companyEntity.setUniversityId(universityId);
            CompanyEntity savedEntity = companyRepository.save(companyEntity);
            university.getCompanyList().add(savedEntity);
            companyDetails.setId(savedEntity.getId());
            universityRepository.save(university);
            logger.info("Company created successfully for university ID: {}", universityId);
            return companyDetails;
        } else {
            logger.error(UNIVERSITY_NOT_FOUND, universityId);
            throw new UniversityNotFoundException("University not found with ID: " + universityId);
        }
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
                    logger.error(COMPANY_NOT_FOUND, companyId);
                    return new CompanyNotFoundException("Company with ID " + companyId + " not found.");
                });

        // Verify the company belongs to the university
        if (!company.getUniversityId().equals(universityId)) {
            logger.error("Company with ID: {} is not associated with university ID: {}", companyId, universityId);
            throw new IllegalStateException("Company with ID " + companyId + " is not associated with university ID " + universityId);
        }

        // Fetch the user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error(USER_NOT_FOUND, userId);
                    return new UserNotFoundException("User with ID " + userId + " not found.");
                });

        // Check if the user is already added
        if (company.getSelectedStudents().stream().anyMatch(student -> student.getId().equals(user.getId()))) {
            logger.error(USER_ALREADY_ADDED, userId, companyId);
            throw new IllegalStateException("User with ID " + userId + " is already added to company with ID " + companyId);
        }

        // Add user to the company's selected students
        company.getSelectedStudents().add(user);
        companyRepository.save(company);
        logger.info("User with ID: {} added to company with ID: {} successfully.", userId, companyId);
    }

    public UniversityProfileDto getUniversityProfile(ObjectId universityId) {
        logger.info("Fetching profile for university ID: {}", universityId);
        UniversityEntity university = universityRepository.findById(universityId)
                .orElseThrow(() -> {
                    logger.error(UNIVERSITY_NOT_FOUND, universityId);
                    return new UniversityNotFoundException("University not found with ID: " + universityId);
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
