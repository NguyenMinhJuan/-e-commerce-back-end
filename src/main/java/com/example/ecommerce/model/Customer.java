package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatar;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> customerOrders;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
