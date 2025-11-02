package com.example.Security;


import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	    @Value("${jwt.secret}")
	    private String secret;

	    @Value("${jwt.expiration}")
	    private long expirationTime; 

	    @Value("${jwt.refreshExpiration}") 
	    private long refreshExpirationTime;

	    private SecretKey key;
	    
	    
	    
	    @PostConstruct
	    public void init() {
	        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	    }

	    // Generate access token
	    public String generateAccessToken(String email, String role) {
	        return buildToken(email, role, expirationTime);
	    }

	    // Generate refresh token
	    public String generateRefreshToken(String email, String role) {
	        return buildToken(email, role, refreshExpirationTime);
	    }

	    // Common token builder
	    private String buildToken(String email, String role, long expiryMinutes) {
	        Instant now = Instant.now();
	        return Jwts.builder()
	                .setSubject(email)            // email as username
	                .claim("role", role)          // role claim
	                .setIssuedAt(Date.from(now))
	                .setExpiration(Date.from(now.plusSeconds(expiryMinutes * 60)))
	                .signWith(SignatureAlgorithm.HS256, key)
	                .compact();
	    }

	    // Extract email (username) from token
	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public String extractRole(String token) {
	        String role = (String) extractAllClaims(token).get("role");
	        return "ROLE_" + role.toUpperCase();
	    }


	    // Validate token with email
	    public boolean validateToken(String token, String username) {
	        return username.equals(extractUsername(token)) && !isTokenExpired(token);
	    }

	    // Generic claim extractor
	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        return claimsResolver.apply(extractAllClaims(token));
	    }

	    // Extract all claims
	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	    }

	    // Check if token is expired
	    private boolean isTokenExpired(String token) {
	        return extractClaim(token, Claims::getExpiration).before(new Date());
	    }


}
