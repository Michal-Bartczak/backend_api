package com.magazyn.backendapi.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}