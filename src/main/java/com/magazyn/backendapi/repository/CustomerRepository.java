package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUsername(String username);
    @Query("SELECT c.id FROM Customer c WHERE c.username = :username")
    Long findIdByUsername(@Param("username") String username);
}
