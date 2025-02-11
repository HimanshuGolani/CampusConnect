package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.dto.LeetcodeDataDto;
import com.campusconnect.CampusConnect.service.LeetcodeLeaderBoardService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


import java.util.Map;

@RestController
@RequestMapping("/api/v1/leetcode")
public class LeetcodeLeaderboardController {

    private final LeetcodeLeaderBoardService leetcodeLeaderBoardService;

    @Autowired
    private LeetcodeLeaderboardController(LeetcodeLeaderBoardService leetcodeLeaderBoardService){
        this.leetcodeLeaderBoardService=leetcodeLeaderBoardService;
    }

    @GetMapping("/{universityId}")
    public ResponseEntity<?> getUniversityLeaderBoard(@Valid @PathVariable ObjectId universityId){
        Map<String, LeetcodeDataDto> leaderBoard = leetcodeLeaderBoardService.getLeetCodeData(universityId);
        return new ResponseEntity<>(leaderBoard, HttpStatus.ACCEPTED);
    }
}
