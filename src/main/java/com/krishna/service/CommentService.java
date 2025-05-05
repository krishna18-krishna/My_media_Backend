package com.krishna.service;

import com.krishna.dto.CommentDTO;
import com.krishna.model.CommentModel;
import com.krishna.model.PostModel;
import com.krishna.model.UserModel;
import com.krishna.repository.CommentRepository;
import com.krishna.repository.PostRepository;
import com.krishna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public CommentModel addComment(CommentDTO dto) throws Exception {
        System.out.println("Received DTO: " + dto.getPostId() + ", " + dto.getUserId());

        UserModel user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        PostModel post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new Exception("Post not found"));

        CommentModel comment = new CommentModel(dto.getContent(), user, post);
        return commentRepository.save(comment);
    }

    public List<CommentModel> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId); // Or custom logic if needed
    }
}
