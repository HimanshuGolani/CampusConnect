package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.*;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import com.campusconnect.CampusConnect.exception.UniversityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final DtoHelperClass dtoHelper;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    // Constants for repeated messages
    private static final String UNIVERSITY_NOT_FOUND = "University not found with ID: {}";
    private static final String USER_NOT_FOUND = "User with email: {} not found.";
    private static final String UNIVERSITY_LOGIN_FAILED = "University login failed for email: {}";

    public AuthService(UserRepository userRepository, UniversityRepository universityRepository, DtoHelperClass dtoHelper) {
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
        this.dtoHelper = dtoHelper;
    }

    @Transactional
    public void userSignUp(@Valid UserDTO userData) {
        UserEntity userEntity = dtoHelper.UserDtoToEntityMapper(userData);
        Optional<UniversityEntity> universityOptional = universityRepository.findById(userData.getUniversityId());
        if (universityOptional.isPresent()) {
            UniversityEntity university = universityOptional.get();
            List<UserEntity> allStudents = university.getAllStudents();
            if (allStudents == null) {
                allStudents = new ArrayList<>();
            }
            allStudents.add(userEntity);
            university.setAllStudents(allStudents);
            userRepository.save(userEntity);
            universityRepository.save(university);
            logger.info("User signed up successfully: {} in University ID: {}", userData.getEmail(), userData.getUniversityId());
        } else {
            logger.error(UNIVERSITY_NOT_FOUND, userData.getUniversityId());
            throw new UniversityNotFoundException("University not found with ID: " + userData.getUniversityId());
        }
    }

    public UserLoginDto userLogin(@Valid LoginDTO userData) {
        Optional<UserEntity> user = userRepository.findByEmail(userData.getEmail());
        UserLoginDto loginResponse = new UserLoginDto();

        if (user.isEmpty()) {
            logger.error(USER_NOT_FOUND, userData.getEmail());
            loginResponse.setLoginStatus(false);
            return loginResponse;
        }

        if (user.get().getPassword().equals(userData.getPassword())) {
            loginResponse.setLoginStatus(true);
            loginResponse.setUserName(user.get().getUserName());
            loginResponse.setId(user.get().getId());
            loginResponse.setUniversityId(user.get().getUniversityId());
            logger.info("User logged in successfully: {}", userData.getEmail());
        } else {
            loginResponse.setLoginStatus(false);
            logger.warn("Incorrect password for user with email: {}", userData.getEmail());
        }
        return loginResponse;
    }

    // University login
    public UniversityLoginDto universityLogin(@Valid LoginDTO universityData) {
        Optional<UniversityEntity> university = universityRepository.findByEmail(universityData.getEmail());
        UniversityLoginDto loginResponse = new UniversityLoginDto();

        if (university.isEmpty()) {
            logger.error(UNIVERSITY_LOGIN_FAILED, universityData.getEmail());
            loginResponse.setLogin_Status(false);
            return loginResponse;
        }

        if (university.get().getPassword().equals(universityData.getPassword())) {
            loginResponse.setLogin_Status(true);
            loginResponse.setId(university.get().getId());
            loginResponse.setUniversityName(university.get().getNameOfUniversity());
            logger.info("University logged in successfully: {}", universityData.getEmail());
        } else {
            loginResponse.setLogin_Status(false);
            logger.warn("Incorrect password for university with email: {}", universityData.getEmail());
        }
        return loginResponse;
    }

    @Transactional
    public void universitySignUp(@Valid UniversityDTO universityData) {
        UniversityEntity universityEntity = dtoHelper.UniversityEntityToDtoMapper(universityData);
        universityRepository.save(universityEntity);
        logger.info("University signed up successfully: {}", universityData.getNameOfUniversity());
    }
}
