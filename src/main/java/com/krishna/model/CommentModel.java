package com.krishna.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostModel post;

    // Constructors
    public CommentModel() {}

    public CommentModel(String content, UserModel user, PostModel post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public UserModel getUser() { return user; }
    public void setUser(UserModel user) { this.user = user; }

    public PostModel getPost() { return post; }
    public void setPost(PostModel post) { this.post = post; }
}
