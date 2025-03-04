package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.*;
import com.campusconnect.CampusConnect.entity.CustomPrincipal;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.enums.EmailType;
import com.campusconnect.CampusConnect.enums.RedisKeys;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import com.campusconnect.CampusConnect.exception.UniversityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final DtoHelperClass dtoHelper;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    // Constants for repeated messages
    private static final String UNIVERSITY_NOT_FOUND = "University not found with ID: {}";
    private static final String USER_NOT_FOUND = "User with email: {} not found.";
    private static final String UNIVERSITY_LOGIN_FAILED = "University login failed for email: {}";

    @Autowired
    public AuthService(UserRepository userRepository, UniversityRepository universityRepository, DtoHelperClass dtoHelper, PasswordEncoder passwordEncoder,RedisService redisService,EmailService emailService) {
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
        this.dtoHelper = dtoHelper;
        this.passwordEncoder = passwordEncoder;
        this.redisService=redisService;
        this.emailService=emailService;
    }

    @Transactional
    public void userSignUp(UserDTO userData) {
        try{
            UserEntity userEntity = dtoHelper.UserDtoToEntityMapper(userData);
            userEntity.setPassword(passwordEncoder.encode(userData.getPassword())); // Encrypt password
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
                redisService.removeCache(RedisKeys.LIST_OF_STUDENTS_OF.toString()+userData.getUniversityId());
                emailService.sendMail(EmailType.WELCOME,new String[]{userData.getEmail()},userData.getUserName(),userData.getNameOfUniversity());

                logger.info("User signed up successfully: {} in University ID: {}", userData.getEmail(), userData.getUniversityId());
            } else {
                logger.error(UNIVERSITY_NOT_FOUND, userData.getUniversityId());
                throw new UniversityNotFoundException("University not found with ID: " + userData.getUniversityId());
            }
        }
        catch (Exception e){
            throw new RuntimeException("Error occured"+e);
        }
    }

    public UserLoginDto userLogin( LoginDTO userData) {
        Optional<UserEntity> user = userRepository.findByEmail(userData.getEmail());
        UserLoginDto loginResponse = new UserLoginDto();

        if (user.isEmpty()) {
            logger.error(USER_NOT_FOUND, userData.getEmail());
            loginResponse.setLoginStatus(false);
            return loginResponse;
        }

        if (passwordEncoder.matches(userData.getPassword(), user.get().getPassword())) {
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

    public UniversityLoginDto universityLogin( LoginDTO universityData) {
        Optional<UniversityEntity> university = universityRepository.findByEmail(universityData.getEmail());
        UniversityLoginDto loginResponse = new UniversityLoginDto();

        if (university.isEmpty()) {
            logger.error(UNIVERSITY_LOGIN_FAILED, universityData.getEmail());
            loginResponse.setLogin_Status(false);
            return loginResponse;
        }

        if (passwordEncoder.matches(universityData.getPassword(), university.get().getPassword())) {
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
    public void universitySignUp( UniversityDTO universityData) {
        UniversityEntity universityEntity = dtoHelper.UniversityEntityToDtoMapper(universityData);
        universityEntity.setPassword(passwordEncoder.encode(universityData.getPassword()));
        universityRepository.save(universityEntity);
        redisService.removeCache(RedisKeys.UNIVERSITY_LIST.toString());
        emailService.sendMail(EmailType.WELCOME, new String[]{universityData.getEmail()},universityData.getNameOfUniversity());
        logger.info("University signed up successfully: {}", universityData.getNameOfUniversity());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            return new CustomPrincipal(user.getEmail(), user.getPassword(), "USER");
        }

        Optional<UniversityEntity> universityOptional = universityRepository.findByEmail(email);
        if (universityOptional.isPresent()) {
            UniversityEntity university = universityOptional.get();
            return new CustomPrincipal(university.getEmail(), university.getPassword(), "UNIVERSITY");
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }


}
