package com.magazyn.backendapi.service;

import com.magazyn.backendapi.dto.OrderStatusDTO;
import com.magazyn.backendapi.entity.Order;
import com.magazyn.backendapi.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderByTrackingNumber(String trackingNumber) {
        logger.info("Tracking Number: {}", trackingNumber);
        return orderRepository.findByTrackingNumber(trackingNumber);
    }

    public OrderStatusDTO getOrderStatusByTrackingNumber(String trackingNumber) {

        Order order = orderRepository.findByTrackingNumber(trackingNumber);
        if (order == null) {
            return new OrderStatusDTO(null,"Nie można znaleźć przesyłki o numerze: " + trackingNumber);
        }
        return new OrderStatusDTO(order.getStatus(), order.getTrackingNumber());
    }
}
