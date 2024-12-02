package com.campusconnect.CampusConnect.controller;

import com.campusconnect.CampusConnect.dto.PostDTO;
import com.campusconnect.CampusConnect.entity.PostEntity;
import com.campusconnect.CampusConnect.service.PostService;
import jakarta.validation.Valid;
 import org.bson.types.ObjectId;
 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

   public PostController(PostService postService){
        this.postService = postService;
    }


    //    Creating a post
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO postData , @PathVariable ObjectId userId){
        try {
            postService.createPost(userId,postData);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get a post by id
    @PostMapping("/get/{id}")
    public ResponseEntity<?> getPostById(@PathVariable ObjectId id){
        try {
          Optional<PostEntity> post = Optional.ofNullable(postService.getPostById(id));
          if (post.isEmpty()){
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
          else {
              return new ResponseEntity<>(post , HttpStatus.FOUND);
          }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//  Updating a post
    @PutMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable ObjectId postId , @Valid PostDTO postData){
        try{
            postService.updatePost(postId,postData);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    deleting a post
    @DeleteMapping("/remove/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable ObjectId postId){
        try{
            postService.deletePost(postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<?> searchPostsByTitle(@PathVariable String title) {
        try {
            List<PostEntity> posts = postService.searchPostsByTitle(title);
            if (posts.isEmpty()) {
                return new ResponseEntity<>("No posts found with the given title", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/content/{content}")
    public ResponseEntity<?> searchPostsByContent(@PathVariable String content) {
        try {
            List<PostEntity> posts = postService.searchPostsByContent(content);
            if (posts.isEmpty()) {
                return new ResponseEntity<>("No posts found with the given content", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/tag/{tag}")
    public ResponseEntity<?> searchPostsByTag(@PathVariable String tag) {
        try {
            List<PostEntity> posts = postService.searchPostsByTag(tag);
            if (posts.isEmpty()) {
                return new ResponseEntity<>("No posts found with the given tag", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/text/{searchText}")
    public ResponseEntity<?> searchPostsByText(@PathVariable String searchText) {
        try {
            List<PostEntity> posts = postService.searchPostsByText(searchText);
            if (posts.isEmpty()) {
                return new ResponseEntity<>("No posts found for the search text", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
