package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);

    Admin findByEmail(String email);
}