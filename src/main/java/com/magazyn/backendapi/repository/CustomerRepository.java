package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);

    Customer findByEmail(String email);
}