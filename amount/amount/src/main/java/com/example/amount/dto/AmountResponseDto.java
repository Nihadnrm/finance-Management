package com.example.amount.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AmountResponseDto {
    private  Long id;
    private BigDecimal amount;
    private LocalDateTime depositDate;
    private int duration;
    private LocalDateTime takeBackDate;

    public AmountResponseDto() {
    }

    public AmountResponseDto(LocalDateTime takeBackDate, Long id, BigDecimal amount, LocalDateTime depositDate, int duration) {
        this.takeBackDate = takeBackDate;
        this.id = id;
        this.amount = amount;
        this.depositDate = depositDate;
        this.duration = duration;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(LocalDateTime depositDate) {
        this.depositDate = depositDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getTakeBackDate() {
        return takeBackDate;
    }

    public void setTakeBackDate(LocalDateTime takeBackDate) {
        this.takeBackDate = takeBackDate;
    }
}
