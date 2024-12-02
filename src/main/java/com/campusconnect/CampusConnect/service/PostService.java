package com.campusconnect.CampusConnect.service;

import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.entity.PostEntity;
import com.campusconnect.CampusConnect.entity.UniversityEntity;
import com.campusconnect.CampusConnect.entity.UserEntity;
import com.campusconnect.CampusConnect.repositories.PostRepository;
import com.campusconnect.CampusConnect.repositories.UniversityRepository;
import com.campusconnect.CampusConnect.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UniversityRepository universityRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository, UniversityRepository universityRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.universityRepository = universityRepository;
    }

    // Creating a post
    @Transactional
    public void createPost(ObjectId userId, PostDTO postData) {
        try {
            System.out.println("The user id is : " + userId);
            Optional<UserEntity> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                System.out.println("User found: " + user);

                // Convert DTO to Entity
                PostEntity post = DtoToObjMapping(postData);
                System.out.println("Post created: " + post);

                // Set references
                post.setUsersId(userId);
                post.setUniversityId(user.getUniversityId());

                System.out.println("Post universityId is saved and userId is saved to.");
                System.out.println(post);

                // Save post
                postRepository.save(post);

                Optional<UniversityEntity> universityEntityOptional = universityRepository.findById(user.getUniversityId());
                if (universityEntityOptional.isPresent()) {
                    System.out.println("University found");
                    UniversityEntity university = universityEntityOptional.get();
                    university.getUniversityRelatedPosts().add(post);
                    universityRepository.save(university);
                    System.out.println("Updated university: " + university);
                } else {
                    throw new RuntimeException("University not found.");
                }

                // Add post to user's list
                user.getPosts().add(post);
                userRepository.save(user);

            } else {
                throw new RuntimeException("User not found.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            throw new RuntimeException("An error occurred while saving the post.", e);
        }
    }

    // Getting a post by post ID
    public PostEntity getPostById(ObjectId postId) {
        Optional<PostEntity> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            return postOpt.get();
        } else {
            throw new RuntimeException("Post not found.");
        }
    }

    // Updating a post by post ID
    @Transactional
    public void updatePost(ObjectId postId, PostDTO updatedPostData) {
        Optional<PostEntity> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            PostEntity post = postOpt.get();

            // Update fields
            post.setTitle(updatedPostData.getTitle());
            post.setContent(updatedPostData.getContent());
            post.setImageUri(updatedPostData.getImageUri());
            post.setCompanySpecificName_TAG(updatedPostData.getCompanySpecificName_TAG());
            post.setCompanySpecificName_TAGS_List(updatedPostData.getCompanySpecificName_TAGS_List());

            // Save updated post
            postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found.");
        }
    }

    // Deleting a post by post ID
    @Transactional
    public void deletePost(ObjectId postId) {
        try {
            Optional<PostEntity> postOpt = postRepository.findById(postId);
            if (postOpt.isPresent()) {
                PostEntity post = postOpt.get();

                // Delete post from repository
                postRepository.delete(post);

                // Remove reference from the user
                Optional<UserEntity> userOpt = userRepository.findById(post.getUsersId());
                if (userOpt.isPresent()) {
                    UserEntity user = userOpt.get();
                    user.getPosts().remove(post);
                    userRepository.save(user);
                }
            } else {
                throw new RuntimeException("Post not found.");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the post.", e);
        }
    }

    // Search posts by title
    public List<PostEntity> searchPostsByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }

    // Search posts by content (description)
    public List<PostEntity> searchPostsByContent(String content) {
        return postRepository.findByContentContainingIgnoreCase(content);
    }



    // Search posts by tag
    public List<PostEntity> searchPostsByTag(String tag) {
        return postRepository.findByCompanySpecificName_TAGS_ListContainingIgnoreCase(tag);
    }

    // Search posts by text (title, content, etc.)
    public List<PostEntity> searchPostsByText(String searchText) {
        return postRepository.searchByText(searchText);
    }

    // Helper method to map DTO to Entity
    private PostEntity DtoToObjMapping(PostDTO postDTO) {
        PostEntity post = new PostEntity();
        post.setUserName(postDTO.getUserName());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageUri(postDTO.getImageUri());
        post.setCompanySpecificName_TAG(postDTO.getCompanySpecificName_TAG());
        post.setCompanySpecificName_TAGS_List(postDTO.getCompanySpecificName_TAGS_List());
        post.setCreatedAt(new Date());
        return post;
    }
}
