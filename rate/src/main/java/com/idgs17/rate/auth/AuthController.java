package com.idgs17.rate.auth;

import com.idgs17.rate.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwt;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Value("${APP_ADMIN_USER:admin}") private String admin;
    @Value("${APP_ADMIN_PASSWORD:Admin123!}") private String password;
    private String passwordHash;
    public AuthController(JwtService jwt) { this.jwt = jwt; }
    @PostConstruct void initializePasswordHash() { passwordHash = encoder.encode(password); }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req == null || !admin.equals(req.username()) || req.password() == null || !encoder.matches(req.password(), passwordHash))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas"));
        return ResponseEntity.ok(Map.of("accessToken", jwt.issue(admin), "tokenType", "Bearer", "expiresIn", 3600, "role", "ADMIN", "username", admin));
    }
    public record LoginRequest(String username, String password) {}
}
