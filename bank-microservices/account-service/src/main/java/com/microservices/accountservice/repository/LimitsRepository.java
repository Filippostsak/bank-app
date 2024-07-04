package com.microservices.accountservice.repository;

import com.microservices.accountservice.model.Limits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LimitsRepository extends JpaRepository<Limits, Long>{
    Optional<Limits> findByAccountId(Long id);
}
