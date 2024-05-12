package com.magazyn.backendapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://localhost:3000"}
      )
public class AuthController {


    @GetMapping
    @PreAuthorize("hasAnyRole('DRIVER','EMPLOYEE','CUSTOMER', 'ADMIN')")
    public boolean cmsAuth() {
        return true;
    }

}