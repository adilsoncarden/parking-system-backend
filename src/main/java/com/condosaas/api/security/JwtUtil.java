package com.condosaas.api.security;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;


@Component
public class JwtUtil {

    private final String SECRET_KEY = "EstaEsUnaClaveSuperSecretaParaCondoSaaS2026_DebeSerLarga";

    private final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 10;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

}
