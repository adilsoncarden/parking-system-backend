package com.condosaas.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtUtils {

    public static final String CLAIM_PERMISOS = "permisos";
    public static final String CLAIM_ROL = "rol";

    public enum TokenStatus {
        VALID, EXPIRED, INVALID
    }

    @Getter
    public static final class TokenValidation {
        private final TokenStatus status;
        private final Claims claims;

        private TokenValidation(TokenStatus status, Claims claims) {
            this.status = status;
            this.claims = claims;
        }

        public static TokenValidation valid(Claims claims) {
            return new TokenValidation(TokenStatus.VALID, claims);
        }

        public static TokenValidation expired() {
            return new TokenValidation(TokenStatus.EXPIRED, null);
        }

        public static TokenValidation invalid() {
            return new TokenValidation(TokenStatus.INVALID, null);
        }

        public boolean isValid() {
            return status == TokenStatus.VALID;
        }
    }

    private final SecretKey key;
    private final long expirationMs;

    public JwtUtils(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs) {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length < 32) {
            throw new IllegalArgumentException("jwt.secret debe tener al menos 32 bytes para HS256");
        }
        this.key = Keys.hmacShaKeyFor(secretBytes);
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username, String rolNombre, List<String> permisos) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim(CLAIM_ROL, rolNombre)
                .claim(CLAIM_PERMISOS, permisos != null ? permisos : Collections.emptyList())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenValidation validateTokenDetailed(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return TokenValidation.valid(claims);
        } catch (ExpiredJwtException ex) {
            log.warn("Token JWT expirado: {}", ex.getMessage());
            return TokenValidation.expired();
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            log.warn("Token JWT inválido: {}", ex.getMessage());
            return TokenValidation.invalid();
        } catch (Exception ex) {
            log.warn("Error al validar JWT: {}", ex.getMessage());
            return TokenValidation.invalid();
        }
    }

    public boolean validateToken(String token) {
        return validateTokenDetailed(token).isValid();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getPermisosFromToken(String token) {
        Claims claims = getClaims(token);
        Object raw = claims.get(CLAIM_PERMISOS);
        if (raw instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }
        return Collections.emptyList();
    }

    public String getRolFromToken(String token) {
        Object rol = getClaims(token).get(CLAIM_ROL);
        return rol != null ? String.valueOf(rol) : "";
    }
}
