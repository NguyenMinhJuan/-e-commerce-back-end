package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    private Long id;
    private String name;
    private int age;
    private String phone;
    private String address;
    private Long salary;
}
