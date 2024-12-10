package com.example.ecommerce.service.user;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.dto.UserPrinciple;
import com.example.ecommerce.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService implements IUserService , UserDetailsService {
    @Autowired
    IUserRepo userRepo;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder; //lỗi thi xoa doan nay di

    @Override
    public Iterable findAll() {
        return null;
    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Object o) {
            User user = (User) o;
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
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
