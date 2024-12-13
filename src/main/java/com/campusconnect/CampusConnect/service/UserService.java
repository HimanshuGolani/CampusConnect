package com.campusconnect.CampusConnect.service;
import com.campusconnect.CampusConnect.dto.DtoHelperClass;
import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.dto.UserDTO;
import com.campusconnect.CampusConnect.dto.UserProfileDto;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoHelperClass dtoHelper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository , DtoHelperClass dtoHelper ){
        this.userRepository = userRepository;
        this.dtoHelper = dtoHelper;
    }

    public List<PostDTO> getAllPosts(ObjectId userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

         return user.getPosts().stream()
                .map(dtoHelper::PostObjToDTOMapping)
                .collect(Collectors.toList());
    }


    public UserProfileDto getUserProfile(ObjectId userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));
        return new UserProfileDto(
                user.getUserName(),
                user.getEmail(),
                user.getUniversityId(),
                user.getNameOfUniversity(),
                user.getCourse(),
                user.getBranch(),
                user.getPlacementStatement(),
                user.getCurrentCompany()
        );
    }





}
