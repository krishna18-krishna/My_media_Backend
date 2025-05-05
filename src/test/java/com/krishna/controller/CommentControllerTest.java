package com.krishna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.dto.CommentDTO;
import com.krishna.model.CommentModel;
import com.krishna.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentModel mockComment;

    @BeforeEach
    void setup() {
        mockComment = new CommentModel();
        mockComment.setId(1L);
        mockComment.setContent("Test comment");
        // Optional: You can mock user/post if needed in your model
    }

    @Test
    void testAddComment() throws Exception {
        CommentDTO dto = new CommentDTO();
        dto.setContent("Test comment");
        dto.setPostId(100L);
        dto.setUserId(UUID.randomUUID());

        Mockito.when(commentService.addComment(any(CommentDTO.class))).thenReturn(mockComment);

        mockMvc.perform(post("/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test comment"));
    }

    @Test
    void testGetCommentsByPost() throws Exception {
        Mockito.when(commentService.getCommentsByPost(100L)).thenReturn(List.of(mockComment));

        mockMvc.perform(get("/comments/post/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Test comment"));
    }

    @Test
    void testDeletePost() throws Exception {
        Mockito.doNothing().when(commentService).deleteComment(100L);

        mockMvc.perform(delete("/comments/100"))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment deleted successfully."));
    }
}
