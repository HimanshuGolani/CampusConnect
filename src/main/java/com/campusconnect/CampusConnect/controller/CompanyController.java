package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.scrapper.CompanyDetailsScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    private final CompanyDetailsScrapperService scrapperService;

    @Autowired
    public CompanyController(CompanyDetailsScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    // Endpoint to fetch company details from Wikipedia
    @GetMapping("/details")
    public Map<String, String> getCompanyDetails(@RequestParam("name") String companyName) {
        return scrapperService.getCompanyDetailsFromWikipedia(companyName);
    }
}
