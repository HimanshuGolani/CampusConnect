package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.dto.CompanyDTO;
import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.dto.UniversityNameListDTO;
import com.campusconnect.CampusConnect.dto.UserDTO;
import com.campusconnect.CampusConnect.service.PostService;
import com.campusconnect.CampusConnect.service.UniversityService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/university")
public class UniversityController {

    private final UniversityService universityService;
    private final PostService postService;
    @Autowired
    public UniversityController(UniversityService universityService,PostService postService){
        this.universityService = universityService;
        this.postService=postService;
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

    @PostMapping("/createPost/{universityId}")
    public ResponseEntity<?> createUniversityPost(@Valid @PathVariable ObjectId universityId,@Valid @RequestBody PostDTO postDTO){
        try{
            postService.createUniversityPost(universityId,postDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
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


    @DeleteMapping("/remove-company/{companyId}/{universityId}")
    public ResponseEntity<Void> removeCompanyById(
            @PathVariable ObjectId companyId,
            @PathVariable ObjectId universityId
    ){
       try{
           universityService.removeCompanyFromUniversity(universityId,companyId);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       catch (Exception e){
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/search/{universityId}")
    public ResponseEntity<?> searchUserByEmail(
            @RequestParam("email") String searchEmail,
            @PathVariable String universityId) {
        try {
            ObjectId uniId = new ObjectId(universityId); // Convert manually
            UserDTO user = universityService.findUserByEmail(searchEmail, uniId);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (IllegalArgumentException e) {  // Handle invalid ObjectId format
            return ResponseEntity.badRequest().body("Invalid university ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

