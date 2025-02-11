package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.LeetcodeDataDto;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.exception.UniversityNotFoundException;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LeetcodeLeaderBoardService {

    private final UniversityRepository universityRepository;
    private final WebClient webClient;


    public LeetcodeLeaderBoardService(UniversityRepository universityRepository,WebClient webClient){
        this.universityRepository=universityRepository;
        this.webClient=webClient;
    }


    public Map<String,LeetcodeDataDto> getLeetCodeData(ObjectId universityId){

        UniversityEntity university = universityRepository.findById(universityId).orElseThrow(
                () -> new UniversityNotFoundException("University not found in the leaderboard section")
        );

        Set<String> leetCodeUsernames = university.getAllStudents()
                        .stream()
                                .map(UserEntity::getLeetCodeUserName)
                                .filter(Objects::nonNull)
                                .filter(username -> !username.trim().isEmpty())
                                .collect(Collectors.toSet());

        return leetCodeUsernames.stream()
                .collect(Collectors.toMap(
                        username -> username ,
                        this::fetchLeetCodeData
                ));
    }

    private LeetcodeDataDto fetchLeetCodeData(String userName) {
        return webClient.get()
                .uri("https://leetcode-api-faisalshohag.vercel.app/" + userName)
                .retrieve()
                .bodyToMono(LeetcodeDataDto.class).block();
    }
}


