package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.*;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final DtoHelperClass dtoHelper;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    AuthService(UserRepository userRepository, UniversityRepository universityRepository,DtoHelperClass dtoHelper) {
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
        this.dtoHelper=dtoHelper;
    }

    @Transactional
    public void userSignUp(@Valid UserDTO userData) {
        // Map UserDTO to UserEntity
        UserEntity userEntity = dtoHelper.UserDtoToEntityMapper(userData);

         // Fetch the university by the provided ID
        Optional<UniversityEntity> universityOptional = universityRepository.findById(userData.getUniversityId());
        // Check if university is present
        if (universityOptional.isPresent()) {
            UniversityEntity university = universityOptional.get();
            System.out.println(university.toString());

            // Add the student's ID to the university's student list
            List<UserEntity> allStudents = university.getAllStudents();
            if (allStudents != null) {
                allStudents.add(userEntity);
            } else {
                // Initialize the list if it's null
                allStudents = new ArrayList<>();
                allStudents.add(userEntity);
                university.setAllStudents(allStudents);
            }

            // Save the user and update the university entity
           userRepository.save(userEntity);
            universityRepository.save(university);
        } else {
            throw new RuntimeException("University not found with id: " + userData.getUniversityId());
        }
    }

    public UserLoginDto userLogin(@Valid LoginDTO userData) {
        Optional<UserEntity> user = userRepository.findByEmail(userData.getEmail());

        if (user.isEmpty()) {
            UserLoginDto data = new UserLoginDto();
            data.setLoginStatus(false);
            return data;
        }
        if(user.get().getPassword().equals(userData.getPassword())){
            UserLoginDto data = new UserLoginDto(user.get().getUserName(),user.get().getId(),user.get().getUniversityId());
            data.setLoginStatus(true);
            return data;
        }
        UserLoginDto data = new UserLoginDto();
        data.setLoginStatus(false);
        return data;
    }

    // University login
    public UniversityLoginDto universityLogin(@Valid LoginDTO universityData) {
        Optional<UniversityEntity> university = universityRepository.findByEmail(universityData.getEmail());

        if (university.isEmpty()) {
            UniversityLoginDto data = new UniversityLoginDto();
            data.setLogin_Status(false);
            return data;
        }

        if(university.get().getPassword().equals(universityData.getPassword())){
            UniversityLoginDto data = new UniversityLoginDto();
            data.setId(university.get().getId());
            data.setUniversityName(university.get().getNameOfUniversity());
            data.setLogin_Status(true);
            return data;
        }
        UniversityLoginDto data = new UniversityLoginDto();
        data.setLogin_Status(false);
        return data;
    }

    @Transactional
    public void universitySignUp(@Valid UniversityDTO universityData) {
        UniversityEntity universityEntity = dtoHelper.UniversityEntityToDtoMapper(universityData);
        universityRepository.save(universityEntity);
    }


}
