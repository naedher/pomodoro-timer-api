package com.p1g14.pomodoro_timer_api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Service for generating and validating JSON Web Tokens (JWT).
 * Handles signing, claim extraction, and expiration checking.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    /**
     * Generates a JWT token for the given user.
     *
     * @param userDetails the user details used to generate the token
     * @return a signed JWT token containing the user's information
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username embedded in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the JWT token against the provided user details.
     * Ensures the username matches and the token has not expired.
     *
     * @param token the JWT token to validate
     * @param userDetails the user details to validate against
     * @return true if the token is valid; false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token
     * @return true if the token is expired; false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Extracts a specific claim from the JWT token using a claims resolver function.
     *
     * @param token the JWT token
     * @param claimsResolver a function that defines which claim to extract
     * @param <T> the type of the claim
     * @return the extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims (payload data) from the JWT token.
     *
     * @param token the JWT token
     * @return all claims contained within the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Returns the signing key used to sign and verify JWT tokens.
     * The key is derived from a base64-encoded secret.
     *
     * @return the signing key
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
