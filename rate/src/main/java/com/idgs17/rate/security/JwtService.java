package com.idgs17.rate.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;

@Service
public class JwtService {
    private SecretKey key() {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(
                    System.getenv().getOrDefault("JWT_SECRET", "DEV_ONLY_change_this_secret_please")
                            .getBytes(StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
    public String issue(String user) {
        Instant now = Instant.now();
        return Jwts.builder().subject(user).claim("role", "ADMIN")
                .issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(3600)))
                .signWith(key()).compact();
    }
    public String subject(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public boolean valid(String token) {
        try {
            var claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
            return "ADMIN".equals(claims.get("role", String.class)) && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
