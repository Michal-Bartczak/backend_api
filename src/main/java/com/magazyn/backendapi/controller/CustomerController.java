package com.magazyn.backendapi.controller;


import com.magazyn.backendapi.dto.UserDetailsDTO;
import com.magazyn.backendapi.entity.Order;
import com.magazyn.backendapi.service.OrderService;
import com.magazyn.backendapi.service.SecurityService;
import com.magazyn.backendapi.service.UserService;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    public CustomerController(SecurityService securityService, OrderService orderService, UserService userService) {
        this.securityService = securityService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/homepage")
    public List<Order> getCustomerOrders() {
        return orderService.getAllOrdersByCustomerIdSortedByDate();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/details")
    public ResponseEntity<UserDetailsDTO> getAuthenticatedUsername() {
        return ResponseEntity.ok(userService.getCurrentUserUsernameAndRoles());
    }

}
