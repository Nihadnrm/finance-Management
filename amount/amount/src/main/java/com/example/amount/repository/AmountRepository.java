package com.example.amount.repository;

import com.example.amount.entity.Amount;
import jdk.jfr.Registered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

@Registered
public interface AmountRepository extends JpaRepository<Amount,Long>, JpaSpecificationExecutor<Amount> {
    boolean existsByDuration(int duration);

    @Query("select a from Amount a where a.amount between :min and :max")
    Page<Amount> findByAmount(BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Amount>findByUserId(Long userId,Pageable pageable);

    Boolean existsByUserId(Long userId);

    @Query("select a.userId,sum(a.amount) as customersTotal from Amount a   group by  a.userId having a.userId=:userId")
    Object[] findTotalByUserId(Long userId);
}
