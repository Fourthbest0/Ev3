package com.idgs17.rate.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class GatewayJwtFilter extends OncePerRequestFilter {
    private final JwtService jwt;
    public GatewayJwtFilter(JwtService jwt) { this.jwt = jwt; }
    @Override protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/error")
                || request.getMethod().equals("OPTIONS");
    }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ") || !jwt.valid(header.substring(7))) {
            response.setStatus(401); response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token requerido, inválido o expirado\"}"); return;
        }
        String token = header.substring(7);
        var authentication = new UsernamePasswordAuthenticationToken(jwt.subject(token), null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
