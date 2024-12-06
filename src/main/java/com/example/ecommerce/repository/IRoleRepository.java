package com.example.ecommerce.repository;

import com.example.ecommerce.enums.RoleName;
import com.example.ecommerce.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository {
    Role findByName(RoleName name);
}
