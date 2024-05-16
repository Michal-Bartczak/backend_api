package com.magazyn.backendapi.repository;


import com.magazyn.backendapi.dto.FilterOrderDTO;
import com.magazyn.backendapi.entity.Order;
import com.magazyn.backendapi.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CustomOrderRepository {
    List<Order> orderFilter(FilterOrderDTO filter);
    Page<Order> orderFilterWithPagination(FilterOrderDTO filter, Pageable pageable);

    Long countOrdersByStatusForCurrentMonth(OrderStatus status);

}