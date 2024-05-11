package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByUsername(String username);
}
