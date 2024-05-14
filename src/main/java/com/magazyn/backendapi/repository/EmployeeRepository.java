package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);
    @Query("SELECT d.id FROM Driver d WHERE d.username = :username")
    Long findIdByUsername(@Param("username") String username);
}
