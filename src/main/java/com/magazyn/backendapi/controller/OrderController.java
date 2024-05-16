package com.magazyn.backendapi.controller;

import com.magazyn.backendapi.dto.FilterOrderDTO;
import com.magazyn.backendapi.dto.OrderFilterResponseDTO;
import com.magazyn.backendapi.dto.OrderStatusDTO;
import com.magazyn.backendapi.service.OrderService;
import com.magazyn.backendapi.service.ShippingLabelService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final ShippingLabelService shippingLabelService;
    public OrderController(OrderService orderService, ShippingLabelService shippingLabelService) {
        this.orderService = orderService;
        this.shippingLabelService = shippingLabelService;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/track-package-status")
    public ResponseEntity<?> getTrackPackageStatus(@RequestBody Map<String, Object> requestBody) {
        String trackingNumber = (String) requestBody.get("trackingNumber");
        OrderStatusDTO orderStatusDTO = orderService.getOrderStatusByTrackingNumber(trackingNumber);
        return new ResponseEntity<>(orderStatusDTO, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/orders/{trackingNumber}/label")
    public ResponseEntity<byte[]> generateShippingLabel(@PathVariable String trackingNumber) {
        byte[] pdfContent = shippingLabelService.createPdf(trackingNumber); // Tworzenie PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("label-" + trackingNumber + ".pdf").build());
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("order/filter")
    public ResponseEntity<?> filterOrders(@RequestBody FilterOrderDTO filter,
                                          @RequestParam int page,
                                          @RequestParam int size){
        OrderFilterResponseDTO response = orderService.filterOrders(filter, PageRequest.of(page, size));
        return ResponseEntity.ok(response);
    }


}
