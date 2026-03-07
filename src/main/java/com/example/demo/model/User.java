package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {
    @Id private String id;

    @Indexed(unique = true)
    private String email;

    private String name;
    private String password;
    private String avatarColor;        // random color assigned on register
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}
