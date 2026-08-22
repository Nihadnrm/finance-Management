package com.example.amount.repository;

import com.example.amount.entity.Amount;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface AmountRepository extends JpaRepository<Amount,Long> {
    boolean existsByDuration(int duration);
}
