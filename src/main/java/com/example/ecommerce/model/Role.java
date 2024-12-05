package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String roleName;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
