package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.DtoHelperClass;
import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.dto.UserProfileDto;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.exception.UserNotFoundException;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoHelperClass dtoHelper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Constants for repeated messages
    private static final String USER_NOT_FOUND = "User with ID: {} not found.";

    public UserService(UserRepository userRepository, DtoHelperClass dtoHelper) {
        this.userRepository = userRepository;
        this.dtoHelper = dtoHelper;
    }

    public List<PostDTO> getAllPosts(ObjectId userId) {
        logger.info("Fetching all posts for user ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error(USER_NOT_FOUND, userId);
                    return new UserNotFoundException("User not found");
                });

        List<PostDTO> posts = user.getPosts().stream()
                .map(dtoHelper::PostObjToDTOMapping)
                .collect(Collectors.toList());

        logger.info("Found {} posts for user ID: {}", posts.size(), userId);
        return posts;
    }

    public UserProfileDto getUserProfile(ObjectId userId) {
        logger.info("Fetching profile for user ID: {}", userId);

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error(USER_NOT_FOUND, userId);
            return new UserNotFoundException("User not found");
        });

        UserProfileDto userProfile = new UserProfileDto(
                user.getUserName(),
                user.getEmail(),
                user.getUniversityId(),
                user.getNameOfUniversity(),
                user.getCourse(),
                user.getBranch(),
                user.getPlacementStatement(),
                user.getCurrentCompany()
        );

        logger.info("Fetched profile for user ID: {} - Username: {}", userId, user.getUserName());
        return userProfile;
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return User.builder()
                .username(user.getEmail()) // Use email as the username
                .password(user.getPassword()) // Use encrypted password
                .roles("USER") // Customize roles or authorities
                .build();
    }

    @Transactional
    public void addLeetCodeUserName(ObjectId userId,String userName){
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found while adding the profile")
        );
        user.setLeetCodeUserName(userName);
        userRepository.save(user);
    }

}
