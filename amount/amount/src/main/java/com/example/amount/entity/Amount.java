package com.example.amount.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(name ="amount" )
    private BigDecimal amount;
    @CreationTimestamp
    @Column(name = "deposit_Date")
    private LocalDateTime depositDate;
    @Column(name = "duration")
    private int duration;
    private LocalDateTime takeBackDate;

//    public Amount() {
//    }
//
//    public Amount(Long id, BigDecimal amount, LocalDateTime depositDate, int duration, LocalDateTime takeBackDate) {
//        this.id = id;
//        this.amount = amount;
//        this.depositDate = depositDate;
//        this.duration = duration;
//        this.takeBackDate = takeBackDate;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public LocalDateTime getDepositDate() {
//        return depositDate;
//    }
//
//    public void setDepositDate(LocalDateTime depositDate) {
//        this.depositDate = depositDate;
//    }
//
//    public int getDuration() {
//        return duration;
//    }
//
//    public void setDuration(int duration) {
//        this.duration = duration;
//    }
//
//    public LocalDateTime getTakeBackDate() {
//        return takeBackDate;
//    }
//
//    public void setTakeBackDate(LocalDateTime takeBackDate) {
//        this.takeBackDate = takeBackDate;
//    }
}
