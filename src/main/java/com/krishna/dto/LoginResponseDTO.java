package com.krishna.dto;

import java.util.UUID;

public class LoginResponseDTO {
    private UUID userId;
    private String email;
    private String token;
    private String username;
    private String fullName;
    private String profileUrl;

    public LoginResponseDTO(UUID userId, String email, String token, String username, String fullName, String profileUrl) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.token = token;
        this.fullName = fullName;
        this.profileUrl = profileUrl;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
