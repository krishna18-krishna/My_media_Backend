package com.krishna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.dto.PostRequestDTO;
import com.krishna.dto.PostUpdateDTO;
import com.krishna.model.PostModel;
import com.krishna.model.UserModel;
import com.krishna.repository.LikeRepository;
import com.krishna.repository.PostRepository;
import com.krishna.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LikeRepository likeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private UserModel user;
    private PostModel post;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new UserModel();
        user.setId(userId);
        user.setFullName("Krishna");
        user.setUsername("krish");
        user.setEmail("krish@example.com");

        post = new PostModel();
        post.setId(1L);
        post.setContent("Hello world");
        post.setImageUrl("image.jpg");
        post.setUserModel(user);
    }

    @Test
    void createPost_ShouldReturnCreated() throws Exception {
        PostRequestDTO request = new PostRequestDTO(userId, "New post", "img.jpg");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(PostModel.class))).thenReturn(post);

        mockMvc.perform(post("/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createPost_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        PostRequestDTO request = new PostRequestDTO(userId, "Post", "img.jpg");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() throws Exception {
        List<PostModel> posts = List.of(post);
        when(postRepository.findAll()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Hello world"));
    }

    @Test
    void updatePost_ShouldReturnUpdatedPost() throws Exception {
        PostUpdateDTO updateDTO = new PostUpdateDTO("Updated content", "updated.jpg");

        // Simulate that the post has been updated
        post.setContent(updateDTO.getContent());
        post.setImageUrl(updateDTO.getImageUrl());

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(PostModel.class))).thenReturn(post);

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    void updatePost_ShouldReturnNotFound() throws Exception {
        PostUpdateDTO updateDTO = new PostUpdateDTO("No post", "no.jpg");

        when(postRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/posts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePost_ShouldReturnSuccess() throws Exception {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        doNothing().when(likeRepository).deleteByPostId(1L);
        doNothing().when(postRepository).deleteById(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post deleted successfully"));
    }

    @Test
    void deletePost_ShouldReturnNotFound() throws Exception {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/posts/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Post not found"));
    }
}
