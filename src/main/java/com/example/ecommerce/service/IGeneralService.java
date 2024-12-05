package com.example.ecommerce.service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(String id);
    void save(T t);
    void delete(String id);
}
