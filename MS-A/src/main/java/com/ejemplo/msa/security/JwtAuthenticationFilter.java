package com.ejemplo.msa.security;
import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.List;
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException {
  String h=req.getHeader("Authorization"); if(h!=null&&h.startsWith("Bearer ")) { try { var claims=JwtService.parse(h.substring(7)); String role=claims.get("role",String.class); if("ADMIN".equals(role)) { var auth=new UsernamePasswordAuthenticationToken(claims.getSubject(),null,List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))); SecurityContextHolder.getContext().setAuthentication(auth); } } catch(Exception ex) { SecurityContextHolder.clearContext(); res.setStatus(401); res.setContentType("application/json"); res.getWriter().write("{\"error\":\"Token inválido o expirado\"}"); return; } }
  chain.doFilter(req,res);
 }
}
