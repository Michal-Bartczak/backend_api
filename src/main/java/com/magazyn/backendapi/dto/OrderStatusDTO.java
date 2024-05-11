package com.magazyn.backendapi.dto;

import com.magazyn.backendapi.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusDTO {
    private OrderStatus orderStatus;
    private String errorMessage;
}
