package com.krishna.controller;

import com.krishna.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(
            @RequestParam UUID userId,
            @RequestParam Long postId
    ) {
        boolean liked = likeService.toggleLike(userId, postId);
        return ResponseEntity.ok(liked ? "Post liked successfully" : "Post unliked");
    }

    @GetMapping("/count/{postId}")
    public Long getLikeCount(@PathVariable Long postId) {
        return likeService.getLikeCount(postId);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> checkIfUserLikedPost(
            @RequestParam UUID userId,
            @RequestParam Long postId
    ) {
        boolean liked = likeService.hasUserLikedPost(userId, postId);
        return ResponseEntity.ok(liked);
    }
}