package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username is required")
    @Size(min = 6,max = 15,message = "Username must be between 6 and 15 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6,max = 60, message = "Password must be between 6 and 32 characters")
    private String password;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
