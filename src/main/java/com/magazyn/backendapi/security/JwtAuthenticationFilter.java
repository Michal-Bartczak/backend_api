package com.magazyn.backendapi.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> creds = objectMapper.readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.get("username"),
                    creds.get("password")
            );
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String token = Jwts.builder()
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))  // 10 days
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
