package com.example.ecommerce.service.product;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.IProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepo IProductRepo;

    @Override
    public List<Product> findAll() {
        return IProductRepo.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        log.info("Service - Finding all products");
        log.info("Pageable: {}", pageable);
        Page<Product> result = IProductRepo.findAll(pageable);
        log.info("Found {} products", result.getTotalElements());
        return result;
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        log.info("Service - Searching for products with name containing: '{}'", name);
        log.info("Using pageable: {}", pageable);
        
        Page<Product> result = IProductRepo.findByNameContaining(name, pageable);
        
        log.info("Found {} products", result.getTotalElements());
        result.getContent().forEach(product -> 
            log.info("- {} (ID: {})", product.getName(), product.getId())
        );
        
        return result;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return IProductRepo.findById(id);
    }

    @Override
    public Product save(Product product) {
        return IProductRepo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        IProductRepo.deleteById(id);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return IProductRepo.findByCategory(category);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return IProductRepo.findByNameContaining(name);
    }

    @Override
    public List<Product> findByPriceBetween(double minPrice, double maxPrice) {
        return IProductRepo.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public Page<Product> findByCategory(String category, Pageable pageable) {
        log.info("Service - Finding products by category: '{}'", category);
        Page<Product> result = IProductRepo.findByCategory(category, pageable);
        log.info("Found {} products in category '{}'", result.getTotalElements(), category);
        return result;
    }

    @Override
    public Page<Product> findByCategoryAndNameContaining(String category, String name, Pageable pageable) {
        log.info("Service - Finding products by category '{}' and name containing '{}'", category, name);
        Page<Product> result = IProductRepo.findByCategoryAndNameContaining(category, name, pageable);
        log.info("Found {} products matching criteria", result.getTotalElements());
        return result;
    }
}