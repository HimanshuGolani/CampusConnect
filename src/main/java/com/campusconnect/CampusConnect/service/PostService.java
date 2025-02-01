package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.DtoHelperClass;
import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.entity.COMPANY_NAME_TAG;
import com.campusconnect.CampusConnect.entity.PostEntity;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.exception.BadContentException;
import com.campusconnect.CampusConnect.repositories.PostRepository;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import com.campusconnect.CampusConnect.exception.PostNotFoundException;
import com.campusconnect.CampusConnect.exception.UserNotFoundException;
import com.campusconnect.CampusConnect.exception.UniversityNotFoundException;
import com.campusconnect.CampusConnect.util.BlogChecker;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UniversityRepository universityRepository;
    private final DtoHelperClass dtoHelper;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    // Constants
    private static final String POST_NOT_FOUND = "Post not found with ID: {}";
    private static final String USER_NOT_FOUND = "User not found with ID: {}";
    private static final String UNIVERSITY_NOT_FOUND = "University not found with ID: {}";

    public PostService(UserRepository userRepository, PostRepository postRepository, UniversityRepository universityRepository, DtoHelperClass dtoHelper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.universityRepository = universityRepository;
        this.dtoHelper = dtoHelper;
    }

    // Creating a post
    @Transactional
    public void createPost(ObjectId userId, PostDTO postData) {

            logger.info("Attempting to create post for user ID: {}", userId);
            if(!BlogChecker.checkTheContent(postData)){
                throw new BadContentException("You have used bad words in the content please rewrite the content and try again");
            }
            Optional<UserEntity> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                PostEntity post = dtoHelper.PostDtoToObjectMapping(postData);
                post.setUsersId(userId);
                post.setUniversityId(user.getUniversityId());
                postRepository.save(post);

                Optional<UniversityEntity> universityEntityOptional = universityRepository.findById(user.getUniversityId());
                if (universityEntityOptional.isPresent()) {
                    UniversityEntity university = universityEntityOptional.get();
                    university.getUniversityRelatedPosts().add(post);
                    universityRepository.save(university);
                } else {
                    logger.error(UNIVERSITY_NOT_FOUND, user.getUniversityId());
                    throw new UniversityNotFoundException("University not found.");
                }
                user.getPosts().add(post);
                userRepository.save(user);
            } else {
                logger.error(USER_NOT_FOUND, userId);
                throw new UserNotFoundException("User not found.");
            }
    }

    @Transactional
    public void createUniversityPost(ObjectId universityId, PostDTO postDTO) {
        try {
            Optional<UniversityEntity> universityEntityOptional = universityRepository.findById(universityId);
            System.out.println(universityEntityOptional.get());
            if (universityEntityOptional.isPresent()) {
                UniversityEntity universityEntity = universityEntityOptional.get();

                PostEntity post = dtoHelper.PostDtoToObjectMapping(postDTO);

                post.setUsersId(universityId);
                post.setUniversityId(universityId);

                universityEntity.getUniversityPosts().add(post);
                universityEntity.getUniversityRelatedPosts().add(post);

                postRepository.save(post);
                universityRepository.save(universityEntity);
            } else {
                logger.error("University not found during post creation: {}", universityId);
                throw new UniversityNotFoundException("University not found during post creation.");
            }
        } catch (UniversityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while creating a post for university {}: {}", universityId, e.getMessage());
            throw new RuntimeException("Error occurred while creating university post.", e);
        }
    }

    // Getting a post by post ID
    public PostEntity getPostById(ObjectId postId) {
        logger.info("Fetching post with ID: {}", postId);
        Optional<PostEntity> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            return postOpt.get();
        } else {
            logger.error(POST_NOT_FOUND, postId);
            throw new PostNotFoundException("Post not found.");
        }
    }

    // Updating a post by post ID
    @Transactional
    public void updatePost(ObjectId postId, PostDTO updatedPostData) {
        logger.info("Updating post with ID: {}", postId);
        Optional<PostEntity> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            PostEntity post = postOpt.get();
            post.setTitle(updatedPostData.getTitle());
            post.setContent(updatedPostData.getContent());
            post.setImageUri(updatedPostData.getImageUri());
            post.setCompanySpecificNameTAG(updatedPostData.getCompanySpecificName_TAG());
            post.setCompanySpecificName_TAGS_List(updatedPostData.getCompanySpecificName_TAGS_List());

            postRepository.save(post);
            logger.info("Successfully updated post with ID: {}", postId);
        } else {
            logger.error(POST_NOT_FOUND, postId);
            throw new PostNotFoundException("Post not found.");
        }
    }

    // Deleting a post by post ID
    @Transactional
    public void deletePost(ObjectId postId) {
        logger.info("Attempting to delete post with ID: {}", postId);
        try {
            Optional<PostEntity> postOpt = postRepository.findById(postId);
            if (postOpt.isPresent()) {
                PostEntity post = postOpt.get();
                postRepository.delete(post);

                Optional<UserEntity> userOpt = userRepository.findById(post.getUsersId());
                if (userOpt.isPresent()) {
                    UserEntity user = userOpt.get();
                    user.getPosts().remove(post);
                    userRepository.save(user);
                    logger.info("Post with ID: {} deleted successfully", postId);
                }
            } else {
                logger.error(POST_NOT_FOUND, postId);
                throw new PostNotFoundException("Post not found.");
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting post with ID: {}", postId, e);
            throw new RuntimeException("An error occurred while deleting the post.", e);
        }
    }

    // Search posts by title
    public List<PostEntity> searchPostsByTitle(String title) {
        logger.info("Searching posts by title: {}", title);
        return postRepository.findByTitleContainingIgnoreCase(title);
    }

    // Search posts by content (description)
    public List<PostEntity> searchPostsByContent(String content) {
        logger.info("Searching posts by content: {}", content);
        return postRepository.findByContentContainingIgnoreCase(content);
    }

    // Search posts by tag
    public List<PostEntity> searchPostsByTag(String tag) {
        logger.info("Searching posts by tag: {}", tag);
        return postRepository.findByCompanySpecificNameTAG_ListContainingIgnoreCase(tag);
    }

    // Search posts by text (title, content, etc.)
    public List<PostEntity> searchPostsByText(String searchText) {
        logger.info("Searching posts by text: {}", searchText);
        return postRepository.searchByText(searchText);
    }

    public List<PostDTO> getAllPostsForUniversity(ObjectId universityId, int page, int pageSize) {
        logger.info("Fetching posts for university ID: {}", universityId);
        return universityRepository.findById(universityId)
                .map(university -> university.getUniversityRelatedPosts().stream()
                        .skip((long) (page - 1) * pageSize)
                        .limit(pageSize)
                        .map(dtoHelper::PostObjToDTOMapping)
                        .toList())
                .orElse(Collections.emptyList());
    }

    public List<COMPANY_NAME_TAG> getCompany_NAME_TAGsList() {
        return Arrays.stream(COMPANY_NAME_TAG.values()).collect(Collectors.toList());
    }
}
