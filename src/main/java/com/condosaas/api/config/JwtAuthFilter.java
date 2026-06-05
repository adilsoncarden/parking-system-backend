package com.condosaas.api.config;

import com.condosaas.api.security.ApiRequestPaths;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = ApiRequestPaths.resolve(request);

        if (path.startsWith("/api/auth/") || "OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractBearerToken(request);

        if (!StringUtils.hasText(token)) {
            writeUnauthorized(response, "TOKEN_MISSING", "Token requerido");
            return;
        }

        JwtUtils.TokenValidation validation = jwtUtils.validateTokenDetailed(token);
        if (!validation.isValid()) {
            String code = validation.getStatus() == JwtUtils.TokenStatus.EXPIRED
                    ? "TOKEN_EXPIRED"
                    : "TOKEN_INVALID";
            log.warn("JWT {} para {} {}", code, request.getMethod(), path);
            writeUnauthorized(response, code, "Token inválido o expirado");
            return;
        }

        try {
            var claims = validation.getClaims();
            String username = claims.getSubject();
            List<String> permisos = readPermisos(claims.get(JwtUtils.CLAIM_PERMISOS));
            String rol = claims.get(JwtUtils.CLAIM_ROL) != null
                    ? String.valueOf(claims.get(JwtUtils.CLAIM_ROL))
                    : "";

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            if (StringUtils.hasText(rol)) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()));
            }
            for (String permiso : permisos) {
                if (StringUtils.hasText(permiso)) {
                    authorities.add(new SimpleGrantedAuthority(permiso));
                }
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        } catch (Exception ex) {
            log.error("Error al procesar JWT: {}", ex.getMessage());
            writeUnauthorized(response, "TOKEN_INVALID", "Token inválido");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private List<String> readPermisos(Object raw) {
        if (raw instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }
        return List.of();
    }

    private void writeUnauthorized(HttpServletResponse response, String code, String message)
            throws IOException {
        if (response.isCommitted()) {
            return;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), Map.of(
                "code", code,
                "message", message));
    }

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header)) {
            header = request.getHeader("authorization");
        }
        if (!StringUtils.hasText(header)) {
            return null;
        }
        String trimmed = header.trim();
        if (trimmed.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return trimmed.substring(7).trim();
        }
        return trimmed;
    }
}
