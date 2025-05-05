package com.krishna.controller;

import com.krishna.dto.PostRequestDTO;
import com.krishna.dto.PostUpdateDTO;
import com.krishna.model.PostModel;
import com.krishna.model.UserModel;
import com.krishna.repository.LikeRepository;
import com.krishna.repository.PostRepository;
import com.krishna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @PostMapping("/add")
    public ResponseEntity<PostModel> createPost(@RequestBody PostRequestDTO postRequestDTO) {
        try {
            UUID userId = postRequestDTO.getUserId();
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            UserModel user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            PostModel postModel = new PostModel();
            postModel.setUserModel(user);
            postModel.setContent(postRequestDTO.getContent());
            postModel.setImageUrl(postRequestDTO.getImageUrl());

            PostModel savedPost = postRepository.save(postModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // READ: All Posts
    @GetMapping
    public ResponseEntity<List<PostModel>> getAllPosts() {
        try {
            List<PostModel> posts = postRepository.findAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PostModel> updatePost(@PathVariable Long id, @RequestBody PostUpdateDTO postUpdateDTO) {
        try {
            Optional<PostModel> optionalPost = postRepository.findById(id);
            if (optionalPost.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            PostModel post = optionalPost.get();
            post.setContent(postUpdateDTO.getContent());
            post.setImageUrl(postUpdateDTO.getImageUrl());

            PostModel updatedPost = postRepository.save(post);
            return ResponseEntity.ok(updatedPost);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            Optional<PostModel> optionalPost = postRepository.findById(id);
            if (optionalPost.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
            }

            // Delete associated likes first
            likeRepository.deleteByPostId(id);


            postRepository.deleteById(id);
            return ResponseEntity.ok("Post deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
