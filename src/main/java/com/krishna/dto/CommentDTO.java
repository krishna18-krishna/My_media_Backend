package com.krishna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CommentDTO {
    @JsonProperty("userId")
    private UUID userId;
    @JsonProperty("postId")
    private Long postId;

    private String content;

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
