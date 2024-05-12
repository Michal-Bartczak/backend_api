package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByUsername(String username);
}
