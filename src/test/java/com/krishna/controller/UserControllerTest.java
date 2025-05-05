package com.krishna.controller;

import com.krishna.model.UserModel;
import com.krishna.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private ObjectMapper objectMapper;
    private UserModel user;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        String rawPassword = "Password@123";
        String hashedPassword = DigestUtils.sha1Hex(rawPassword); // from Apache Commons Codec

        user = new UserModel("Krishna Dev", "krish123", "krishna@example.com", hashedPassword);
        user.setId(UUID.randomUUID());
    }

    @Test
    void registerUser_Success() throws Exception {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void registerUser_EmailAlreadyExists() throws Exception {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already registered."));
    }

    @Test
    void loginUser_Success() throws Exception {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", user.getEmail());
        loginRequest.put("password", "Password@123");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("krishna@example.com"))
                .andExpect(jsonPath("$.username").value("krish123"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void loginUser_InvalidPassword() throws Exception {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", user.getEmail());
        loginRequest.put("password", "WrongPassword123");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid password."));
    }

    @Test
    void loginUser_UserNotFound() throws Exception {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found."));
    }
}
