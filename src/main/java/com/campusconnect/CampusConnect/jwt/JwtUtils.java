//package com.campusconnect.CampusConnect.jwt;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class JwtUtils {
//
//    @Value("${spring.app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${spring.app.jwtExpirationMs}")
//    private int jwtExpirationMs;
//
//    // Extract JWT token from Authorization header
//    public String getJwtFromHeader(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);  // Extract token after "Bearer "
//        }
//        return null;  // Return null if not present or not in correct format
//    }
//
//    // Generate JWT token from user email
//    public String generateTokenFromUserEmail(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(getKey())  // Sign the JWT token with the key
//                .compact();
//    }
//
//    // Extract email from JWT token
//    public String getEmailFromJwtToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    // Validate the JWT token
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(authToken);
//            return true;  // Token is valid
//        } catch (JwtException e) {
//            log.error("Invalid JWT token: {}", e.getMessage());
//            return false;  // Token is invalid
//        }
//    }
//
//    // Get the signing key for JWT
//    private Key getKey() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));  // Secure key generation
//    }
//}
////