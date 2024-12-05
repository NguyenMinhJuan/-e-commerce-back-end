package com.example.ecommerce.model;

import com.example.ecommerce.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private RoleName name;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;
}
