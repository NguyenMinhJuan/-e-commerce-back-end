package com.example.ecommerce.service.product;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
    List<Product> findByCategory(String category);
    List<Product> findByNameContaining(String name);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByCategoryAndNameContaining(String category, String name, Pageable pageable);
}