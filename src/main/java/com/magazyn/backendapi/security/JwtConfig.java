package com.magazyn.backendapi.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}