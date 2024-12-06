package com.example.ecommerce.service.user;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.IGenerateService;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService extends IGenerateService {
    public UserDetails loadUserByUsername(String username);
    User findByUsername(String username);
}
