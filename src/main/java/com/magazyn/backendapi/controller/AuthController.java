package com.magazyn.backendapi.controller;

import com.magazyn.backendapi.dto.AuthRequestDTO;
import com.magazyn.backendapi.dto.JwtResponseDTO;
import com.magazyn.backendapi.service.JwtService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/driver")
    @PreAuthorize("hasAnyRole('DRIVER')")
    public boolean driverAuth() {
        return true;
    }

    @PostMapping("/customer")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public boolean customerAuth() {
        return true;
    }

    @PostMapping("/employee")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public boolean employeeAuth() {
        return true;
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public boolean adminAuth() {
        return true;
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        if (authentication.isAuthenticated()) {
            long sessionTimeInHours = 20; // 20 hours
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername(), sessionTimeInHours))
                    .build();
        } else {
            throw new UsernameNotFoundException("Not authorized - invalid user request");
        }
    }
}
