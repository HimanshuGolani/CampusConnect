package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.scrapper.CompanyDetailsScrapperService;
import com.campusconnect.CampusConnect.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    private final CompanyDetailsScrapperService scrapperService;
    private final PostService postService;

    @Autowired
    public CompanyController(CompanyDetailsScrapperService scrapperService, PostService postService) {
        this.scrapperService = scrapperService;
        this.postService=postService;
    }

    // Endpoint to fetch company details from Wikipedia
    @GetMapping("/details")
    public ResponseEntity<?> getCompanyDetails(@RequestParam("name") String companyName ,@Valid @RequestParam("universityId") ObjectId universityId) throws JsonProcessingException {
        System.out.println("Entered the get details"+ companyName + " "+ universityId);
        Map<String, String> scrapperData = scrapperService.getCompanyDetailsFromWikipedia(companyName);
        List<PostDTO> relatedPosts = postService.findPostsRelatedToCompany(universityId,companyName);
        System.out.println(scrapperData.toString());
        System.out.println(relatedPosts.toString());
        return new ResponseEntity<>(List.of(relatedPosts,scrapperData) , HttpStatus.OK);
    }
}
