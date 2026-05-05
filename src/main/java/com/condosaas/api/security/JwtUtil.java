package com.condosaas.api.security;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "EstaEsUnaClaveSuperSecretaParaCondoSaaS2026_DebeSerLarga";

    private final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 10;

}
