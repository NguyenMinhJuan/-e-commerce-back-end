package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CustomerOrder {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
