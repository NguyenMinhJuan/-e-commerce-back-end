package com.example.ecommerce.model;

import com.example.ecommerce.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;
}
