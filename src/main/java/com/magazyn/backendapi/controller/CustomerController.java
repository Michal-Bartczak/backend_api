package com.magazyn.backendapi.controller;


import com.magazyn.backendapi.entity.Order;
import com.magazyn.backendapi.service.OrderService;
import com.magazyn.backendapi.service.SecurityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final SecurityService securityService;
    private final OrderService orderService;

    public CustomerController(SecurityService securityService, OrderService orderService) {
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/homepage")
    public List<Order> getCustomerOrders() {
        return orderService.getAllOrdersByCustomerIdSortedByDate();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/username")
    public String getAuthenticatedUsername() {
        return securityService.getAuthenticatedUsername();
    }
}
