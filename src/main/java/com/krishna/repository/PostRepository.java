package com.krishna.repository;

import com.krishna.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
    List<PostModel> findByUserModelId(UUID id);
}
