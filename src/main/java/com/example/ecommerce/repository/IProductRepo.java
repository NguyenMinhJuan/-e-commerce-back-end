package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByCategoryAndNameContaining(String category, String name, Pageable pageable);
}