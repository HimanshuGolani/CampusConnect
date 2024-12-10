package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.dto.CompanyDTO;
import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.dto.UniversityNameListDTO;
import com.campusconnect.CampusConnect.dto.UserDTO;
import com.campusconnect.CampusConnect.service.UniversityService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/university")
public class UniversityController {

    private final UniversityService universityService;
    public UniversityController(UniversityService universityService){
        this.universityService = universityService;
    }

    @GetMapping("/universityList")
        public ResponseEntity<?> listOfUniversity(){
      try{
          List<UniversityNameListDTO> universityList = universityService.getAllUniversities();
          return ResponseEntity.ok(universityList);
      }catch (Exception e){
          return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PostMapping("/{universityId}/listOfStudents")
    public ResponseEntity<?> getAllStudents(@PathVariable ObjectId universityId){
        try{
            List<UserDTO> students = universityService.findAllStudents(universityId);
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createCompany/{universityId}")
    public ResponseEntity<?> createCompany(@RequestBody CompanyDTO companyDetails ,@Valid @PathVariable ObjectId universityId ){
        try{
            CompanyDTO savedEntity = universityService.createCompany(universityId,companyDetails);
            return new ResponseEntity<>(savedEntity , HttpStatus.CREATED);
        }
        catch (Exception e){
         return new ResponseEntity<>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListOfCompanies/{universityId}")
    public ResponseEntity<?> getAllCompaniesVisited(@Valid @PathVariable ObjectId universityId){
        try{
            List<CompanyDTO> companies = universityService.findAllCompaniesVisiting(universityId);
            return new ResponseEntity<>(companies,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addStudent/{universityId}/{companyId}/{userId}")
        public ResponseEntity<?> addStudentToCompany(@PathVariable ObjectId universityId , @PathVariable ObjectId companyId , @PathVariable ObjectId userId){
        try{
            universityService.addStudentToCompany(userId,universityId,companyId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/myProfile/{universityId}")
    public ResponseEntity<?> getUniversityProfile(@Valid @PathVariable ObjectId universityId){
        try {
                return new ResponseEntity<>(universityService.getUniversityProfile(universityId),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/search/{universityId}")
    public ResponseEntity<?> searchUserByEmail(@RequestParam("email") String searchEmail , @Valid @PathVariable ObjectId universityId) {
        try {
            UserDTO user = universityService.findUserByEmail(searchEmail,universityId);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







}

// List of functions to be implemented

// 1. Show the list of all the students. (DONE)
// 2. show the list of all the companies visited. (DONE)
// 3. Adding posts to the community that are visible to all the students of the university
// 4. Having the admin access that can delete users particular post
// 5. Uploading the list of students per company. (DONE)
// 6. Updating/editing own posts and deleting them. (DONE)