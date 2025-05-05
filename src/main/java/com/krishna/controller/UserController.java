package com.krishna.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.krishna.dto.LoginResponseDTO;
import com.krishna.model.UserModel;
import com.krishna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final String JWT_SECRET = "your_secret_key";  // Replace with a secure secret in production

    // 🔐 Utility method to hash password with SHA-1
    private String hashPasswordSHA1(String password) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = sha1.digest(password.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // 📝 Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email, password and username are required.");
            }

            Optional<UserModel> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered.");
            }

            // 🔐 Hash password before storing
            String hashedPassword = hashPasswordSHA1(user.getPassword());
            user.setPassword(hashedPassword);

            // ✅ Set default profile image if not provided
            if (user.getProfileUrl() == null || user.getProfileUrl().isEmpty()) {
                user.setProfileUrl("../assets/images/profile-pic.jpg");
            }

            UserModel savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    // 🔐 Login a user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel loginRequest) {
        try {
            if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password are required.");
            }

            Optional<UserModel> userOpt = userRepository.findByEmail(loginRequest.getEmail());
            if (userOpt.isPresent()) {
                UserModel user = userOpt.get();

                // 🔐 Hash incoming password
                String hashedInputPassword = hashPasswordSHA1(loginRequest.getPassword());

                // ✅ Match hashed password
                if (user.getPassword().equals(hashedInputPassword)) {
                    String token = generateToken(user);

                    LoginResponseDTO response = new LoginResponseDTO(
                            user.getId(),
                            user.getEmail(),
                            token,
                            user.getUsername(),
                            user.getFullName(),
                            user.getProfileUrl()
                    );

                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login error: " + e.getMessage());
        }
    }

    // 🔐 JWT Token generation
    private String generateToken(UserModel user) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("fullName", user.getFullName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(algorithm);
    }

    // 🔍 Search users by username or fullName
    @GetMapping("/search")
    public ResponseEntity<List<UserModel>> searchUsers(@RequestParam("query") String query) {
        List<UserModel> users = userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(query, query);
        return ResponseEntity.ok(users);
    }

}
