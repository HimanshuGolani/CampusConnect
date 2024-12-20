package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
public class UtilityController {

    @Autowired
    private AppCache appCache;

    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.inti();
    }

}
