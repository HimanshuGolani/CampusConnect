package com.campusconnect.CampusConnect.dto;

import com.campusconnect.CampusConnect.entity.CompanyEntity;
import com.campusconnect.CampusConnect.entity.PostEntity;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DtoHelperClass {

    public UserDTO mapToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUniversityId(userEntity.getUniversityId());
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setBranch(userEntity.getBranch());
        userDTO.setUniversityReg(userDTO.getUniversityReg());
        userDTO.setCurrentCompany(userEntity.getCurrentCompany());
        return userDTO;
    }

    public UserEntity UserDtoToEntityMapper(UserDTO dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(dto.getEmail());
        userEntity.setUniversityId(dto.getUniversityId());
        userEntity.setPassword(dto.getPassword());
        userEntity.setUserName(dto.getUserName());
        userEntity.setNameOfUniversity(dto.getNameOfUniversity());
        userEntity.setUniversityReg(dto.getUniversityReg());
        userEntity.setCourse(dto.getCourse());
        userEntity.setBranch(dto.getBranch());
        userEntity.setCurrentCompany(dto.getCurrentCompany());
        userEntity.setPlacementStatement(dto.getPlacementStatement());
        return userEntity;
    }

    public UniversityEntity UniversityEntityToDtoMapper(UniversityDTO dto) {
        UniversityEntity universityEntity = new UniversityEntity();
        universityEntity.setEmail(dto.getEmail());
        universityEntity.setPassword(dto.getPassword());
        universityEntity.setNameOfUniversity(dto.getNameOfUniversity());
        universityEntity.setOfficerHead(dto.getOfficerHead());
        universityEntity.setEstablishedIn(dto.getEstablishedIn());
        universityEntity.setNoOfCompanyVisit(dto.getNoOfCompanyVisit());
        universityEntity.setNirfRanking(dto.getNirfRanking());
        universityEntity.setLocationOfUniversity(dto.getLocationOfUniversity());
        return universityEntity;
    }

    public CompanyEntity mapToCompanyEntity(CompanyDTO companyDetails){
        CompanyEntity companyEntity1 = new CompanyEntity();
        companyEntity1.setCompanyName(companyDetails.getCompanyName());
        companyEntity1.setUniversityId(companyDetails.getUniversityId());
        return companyEntity1;
    }

    public CompanyDTO mapToCompanyDTO(CompanyEntity companyDetails){
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(companyDetails.getId());
        companyDTO.setCompanyName(companyDetails.getCompanyName());
        companyDTO.setUniversityId(companyDetails.getUniversityId());
        return companyDTO;
    }


    public PostEntity PostDtoToObjectMapping(PostDTO postDTO) {
        PostEntity post = new PostEntity();
        post.setUserName(postDTO.getUserName());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageUri(postDTO.getImageUri());
        post.setCompanySpecificNameTAG(postDTO.getCompanySpecificName_TAG());
        post.setCompanySpecificName_TAGS_List(postDTO.getCompanySpecificName_TAGS_List());
        post.setCreatedAt(new Date());
        return post;
    }


    public PostDTO PostObjToDTOMapping(PostEntity postData) {
        return new PostDTO(
                postData.getId(),
                postData.getUsersId(),
                postData.getUserName(),
                postData.getTitle(),
                postData.getContent(),
                postData.getImageUri());
    }

}
