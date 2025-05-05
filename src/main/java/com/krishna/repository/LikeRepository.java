package com.krishna.repository;

import com.krishna.model.LikeModel;
import com.krishna.model.PostModel;
import com.krishna.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeModel, Long> {

    Optional<LikeModel> findByUserAndPost(UserModel user, PostModel post);

    Long countByPost(PostModel post);

    @Transactional
    @Modifying
    @Query("DELETE FROM LikeModel l WHERE l.post.id = :postId")
    void deleteByPostId(Long postId);
}