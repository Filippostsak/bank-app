package com.microservices.userservice.util;

import com.microservices.userservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT token generation and validation
 */
@Component
public class JwtTokenProvider {

    /**
     * JWT secret key
     */

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * JWT expiration time
     */

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    /**
     * Date format for JWT token
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Generate JWT token
     * @return JWT token
     */
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("firstname", user.getFirstname());
        claims.put("lastname", user.getLastname());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().name());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        claims.put("issuedAt", dateFormat.format(now));
        claims.put("expiresAt", dateFormat.format(expiryDate));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Validate JWT token
     * @param token JWT token
     * @return true if token is valid, false otherwise
     */

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            // Token has expired
            throw new RuntimeException("Token has expired");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    /**
     * Get username from JWT token
     * @param token JWT token
     * @return username
     */

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Get claim from JWT token
     * @param token JWT token
     * @param claimsResolver Claims resolver
     * @return claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Get all claims from JWT token
     * @param token JWT token
     * @return claims
     */

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
