package com.example.ecommerce.service;

import com.example.ecommerce.configuration.jwt.JwtTokenProvider;
import com.example.ecommerce.model.dto.AuthRequest;
import com.example.ecommerce.model.dto.AuthResponse;
import com.example.ecommerce.repository.IUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

//@Service
//public class AuthService {
//    private final AuthenticationManager authenticationManager;
//    private final IUserRepository userRepository;
//    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    private static final long EXPIRATION_TIME = 864_000_000; // 10 ngày
//
//    public AuthService(
//            AuthenticationManager authenticationManager,
//            IUserRepository userRepository
//    ) {
//        this.authenticationManager = authenticationManager;
//        this.userRepository = userRepository;
//    }
//
//    public AuthResponse authenticate(AuthRequest request) {
//        // Xác thực người dùng
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//        // Thiết lập context security
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Tạo token
//        String token = generateToken(authentication);
//
//        // Lấy role đầu tiên của user
//        String role = authentication.getAuthorities().iterator().next().getAuthority();
//
//        AuthResponse response = AuthResponse.builder()
//                .token(token)
//                .username(request.getUsername())
//                .role(role)
//                .build();
//
//        return response;
//    }
//
//    private String generateToken(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}


@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(authentication);
        return AuthResponse.builder()
                .token(token)
                .username(request.getUsername())
                .build();
    }
}