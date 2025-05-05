package com.krishna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PostUpdateDTO {
    private String content;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("userId")
    private UUID userId;

    public PostUpdateDTO( String content, String imageUrl) {
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
