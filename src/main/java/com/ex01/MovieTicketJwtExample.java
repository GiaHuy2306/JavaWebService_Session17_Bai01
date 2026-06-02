package com.ex01;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieTicketJwtExample {

    public static void main(String[] args) {

        // 1. Tạo Secret Key
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // 2. Khai báo claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 123L);
        claims.put("roles", "USER");

        // 3. Thời gian phát hành và hết hạn
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600 * 1000);

        // 4. Tạo JWT
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject("user@movieticket.com")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Generated JWT: " + jwtToken);

        try {

            // Xác minh bằng CHÍNH key đã dùng để ký
            Claims body = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            System.out.println("JWT is valid and verified!");

            String subject = body.getSubject();
            Long userId = ((Number) body.get("userId")).longValue();
            String roles = (String) body.get("roles");

            System.out.println("Subject: " + subject);
            System.out.println("User ID: " + userId);
            System.out.println("Roles: " + roles);

        } catch (Exception e) {
            System.err.println("Invalid JWT: " + e.getMessage());
        }
    }
}