package com.krishna.service;

import com.krishna.model.LikeModel;
import com.krishna.model.PostModel;
import com.krishna.model.UserModel;
import com.krishna.repository.LikeRepository;
import com.krishna.repository.PostRepository;
import com.krishna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    public boolean toggleLike(UUID userId, Long postId) {
        Optional<UserModel> userOpt = userRepository.findById(userId);
        Optional<PostModel> postOpt = postRepository.findById(postId);

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            return false;
        }

        UserModel user = userOpt.get();
        PostModel post = postOpt.get();

        Optional<LikeModel> likeOpt = likeRepository.findByUserAndPost(user, post);

        if (likeOpt.isPresent()) {
            likeRepository.delete(likeOpt.get());
            return false;
        } else {
            LikeModel newLike = new LikeModel(user, post);
            likeRepository.save(newLike);
            return true;
        }
    }

    public Long getLikeCount(Long postId) {
        Optional<PostModel> postOpt = postRepository.findById(postId);
        return postOpt.map(likeRepository::countByPost).orElse(0L);
    }

    public boolean hasUserLikedPost(UUID userId, Long postId) {
        Optional<UserModel> userOpt = userRepository.findById(userId);
        Optional<PostModel> postOpt = postRepository.findById(postId);

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            return false;
        }

        return likeRepository.findByUserAndPost(userOpt.get(), postOpt.get()).isPresent();
    }
}
