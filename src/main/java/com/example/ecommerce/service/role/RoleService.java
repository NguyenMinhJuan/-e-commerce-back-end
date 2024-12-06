package com.example.ecommerce.service.role;

import com.example.ecommerce.repository.IProductRepo;
import com.example.ecommerce.repository.IRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    IRoleRepo roleRepo;

    @Override
    public Iterable findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void delete(Long id) {

    }
}
