package com.example.gateway.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    private String username;

    private String password;

    @Column(nullable = false)
    private String role;
}
