package com.example.ecommerce.service.user;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.dto.UserPrinciple;
import com.example.ecommerce.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService , UserDetailsService {
    @Autowired
    IUserRepo userRepo;

    @Override
    public Iterable findAll() {
        return null;
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

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username);
        return UserPrinciple.build(user);
    }
}
