package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    private Long id;
    private String name;
    private String avatar;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> customerOrders;
}
