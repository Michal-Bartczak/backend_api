package com.magazyn.backendapi.dto;

import com.magazyn.backendapi.entity.Driver;
import com.magazyn.backendapi.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilterResponseDTO {
    private List<Order> orders;
    private String message;
    private List<Driver> drivers;
    private int totalPages;
}
