package com.backend.backend.util;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        
        log.debug("Checking if should filter: {} {}", method, path);
        
        boolean shouldNotFilter = 
            path.equals("/email-send") ||
            path.equals("/signin") ||
            path.equals("/signup") ||
            path.equals("/auth/google") ||
            path.equals("/health") ||
            path.equals("/error") ||
            path.startsWith("/swagger-ui/") ||
            path.startsWith("/v3/api-docs/");
        
        if (shouldNotFilter) {
            log.debug("Skipping JWT filter for: {} {}", method, path);
        }
        
        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        log.debug("Processing JWT filter for: {} {}", request.getMethod(), request.getServletPath());

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            log.debug("JWT token found");

            try {
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    String role = jwtUtil.extractRole(token);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    List.of(new SimpleGrantedAuthority(role))
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                    
                    log.debug("Authentication set for user: {}", email);
                } else {
                    log.warn("Invalid JWT token");
                }
            } catch (Exception e) {
                log.error("Error processing JWT token: {}", e.getMessage());
            }
        } else {
            log.debug("No Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}