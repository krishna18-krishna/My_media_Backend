package com.krishna.repository;

import com.krishna.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    List<CommentModel> findByPostId(Long postId);
}
