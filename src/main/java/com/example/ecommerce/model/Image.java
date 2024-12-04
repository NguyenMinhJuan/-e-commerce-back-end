package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
