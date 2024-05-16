package com.magazyn.backendapi.service;

import com.magazyn.backendapi.dto.FilterOrderDTO;
import com.magazyn.backendapi.dto.OrderFilterResponseDTO;
import com.magazyn.backendapi.dto.OrderStatusDTO;
import com.magazyn.backendapi.entity.Order;
import com.magazyn.backendapi.repository.CustomOrderRepository;
import com.magazyn.backendapi.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final SecurityService securityService;
    private final DriverService driverService;
    private final CustomOrderRepository customOrderRepository;

    public OrderService(OrderRepository orderRepository, SecurityService securityService, DriverService driverService, CustomOrderRepository customOrderRepository) {
        this.orderRepository = orderRepository;
        this.securityService = securityService;
        this.driverService = driverService;
        this.customOrderRepository = customOrderRepository;
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


    public List<Order> getAllOrdersByCustomerIdSortedByDate() {
        String username = securityService.getAuthenticatedUsername();

        return orderRepository.findAllByCustomerIdOrderByCreationDate(securityService.getIdByUsername(username));
    }
    public Page<Order> filterOrdersWithPagination(FilterOrderDTO filterOrderDTO, Pageable pageable){
        return customOrderRepository.orderFilterWithPagination(filterOrderDTO,pageable);
    }
    public OrderFilterResponseDTO filterOrders(FilterOrderDTO filter, Pageable pageable) {
        Page<Order> filteredOrdersPage = filterOrdersWithPagination(filter, pageable);
        OrderFilterResponseDTO response = new OrderFilterResponseDTO();

        if (filteredOrdersPage.isEmpty()) {
            response.setMessage("Żadna z przesyłek nie spełnia kryteriów");
        }
        response.setOrders(filteredOrdersPage.getContent());
        response.setDrivers(driverService.getAllDrivers());
        response.setTotalPages(filteredOrdersPage.getTotalPages());

        return response;
    }

}
