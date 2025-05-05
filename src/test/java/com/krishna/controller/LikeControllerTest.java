package com.krishna.controller;

import com.krishna.service.LikeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @Test
    void toggleLike_ShouldReturnLikedMessage() throws Exception {
        UUID userId = UUID.randomUUID();
        Long postId = 1L;

        when(likeService.toggleLike(userId, postId)).thenReturn(true);

        mockMvc.perform(post("/likes/toggle")
                        .param("userId", userId.toString())
                        .param("postId", postId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Post liked successfully"));
    }

    @Test
    void toggleLike_ShouldReturnUnlikedMessage() throws Exception {
        UUID userId = UUID.randomUUID();
        Long postId = 2L;

        when(likeService.toggleLike(userId, postId)).thenReturn(false);

        mockMvc.perform(post("/likes/toggle")
                        .param("userId", userId.toString())
                        .param("postId", postId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Post unliked"));
    }

    @Test
    void getLikeCount_ShouldReturnCount() throws Exception {
        Long postId = 3L;
        when(likeService.getLikeCount(postId)).thenReturn(42L);

        mockMvc.perform(get("/likes/count/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
    }

    @Test
    void checkIfUserLikedPost_ShouldReturnTrue() throws Exception {
        UUID userId = UUID.randomUUID();
        Long postId = 4L;

        when(likeService.hasUserLikedPost(userId, postId)).thenReturn(true);

        mockMvc.perform(get("/likes/status")
                        .param("userId", userId.toString())
                        .param("postId", postId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void checkIfUserLikedPost_ShouldReturnFalse() throws Exception {
        UUID userId = UUID.randomUUID();
        Long postId = 5L;

        when(likeService.hasUserLikedPost(userId, postId)).thenReturn(false);

        mockMvc.perform(get("/likes/status")
                        .param("userId", userId.toString())
                        .param("postId", postId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
