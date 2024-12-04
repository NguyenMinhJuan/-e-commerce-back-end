package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;
}
