package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.trackingNumber = :trackingNumber")
    Order findByTrackingNumber(@Param("trackingNumber") String trackingNumber);
}
