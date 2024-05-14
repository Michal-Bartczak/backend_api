package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUsername(String username);
    @Query("SELECT a.id FROM Admin a WHERE a.username = :username")
    Long findIdByUsername(@Param("username") String username);

}
