package com.magazyn.backendapi.controller;

import com.magazyn.backendapi.dto.AuthRequestDTO;
import com.magazyn.backendapi.dto.JwtResponseDTO;
import com.magazyn.backendapi.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {


    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController( JwtService jwtService, AuthenticationManager authenticationManager) {

        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody AuthRequestDTO authRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponseDTO
                    .builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername(),
                            3L)).build();
        } else {
            throw new UsernameNotFoundException("Not authorized - invalid user request");
        }
    }
}
