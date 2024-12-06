//package com.example.ecommerce.configuration.jwt;
//
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expiration}")
//    private long validityInMilliseconds = 3600000; // 1 giờ
//
//    public String generateToken(Authentication authentication) {
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//}
//
//
