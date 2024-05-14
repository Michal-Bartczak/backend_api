package com.magazyn.backendapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/driver")
@PreAuthorize("hasRole('ROLE_DRIVER')")
public class DriverController {

    @GetMapping("/data")
    public Map<String, String> getDriverData() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is data available only for drivers.");
        return response;
    }
}
