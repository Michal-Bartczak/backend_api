package com.magazyn.backendapi.controller;

import com.magazyn.backendapi.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final SecretKey secretKey;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.secretKey = secretKey;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) throws AuthenticationException {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 days
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
