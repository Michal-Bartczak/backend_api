package com.magazyn.backendapi.controller;

import com.magazyn.backendapi.dto.OrderStatusDTO;
import com.magazyn.backendapi.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/track-package-status")
    public ResponseEntity<?> getTrackPackageStatus(@RequestBody Map<String, Object> requestBody) {
        String trackingNumber = (String) requestBody.get("trackingNumber");
        OrderStatusDTO orderStatusDTO = orderService.getOrderStatusByTrackingNumber(trackingNumber);
        return new ResponseEntity<>(orderStatusDTO, HttpStatus.OK);
    }


}
