package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String phone;
    private String address;
    private Long salary;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
