package com.ejemplo.msa.security;
import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets; import java.security.MessageDigest; import java.security.NoSuchAlgorithmException; import java.util.Date;
import javax.crypto.SecretKey;
public final class JwtService {
 private JwtService() {}
 private static SecretKey key() { try { byte[] hash=MessageDigest.getInstance("SHA-256").digest(System.getenv().getOrDefault("JWT_SECRET","DEV_ONLY_change_this_secret_please").getBytes(StandardCharsets.UTF_8)); return Keys.hmacShaKeyFor(hash); } catch(NoSuchAlgorithmException e) { throw new IllegalStateException(e); } }
 public static Claims parse(String token) { return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload(); }
}
