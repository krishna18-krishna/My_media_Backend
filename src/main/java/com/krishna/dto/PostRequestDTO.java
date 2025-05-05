package com.krishna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PostRequestDTO {
    private String content;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("userId")
    private UUID userId;

    public PostRequestDTO(UUID userId, String content, String imageUrl) {
        this.userId = userId;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}
