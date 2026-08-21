package com.example.amount.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AmountRequestDto {
    @NotNull
   @DecimalMin("10")
    @DecimalMax("1000000")
    private BigDecimal amount;
    @NotNull
    @Size(min = 1,max = 2)
    private int duration;

    public AmountRequestDto() {
    }

    public AmountRequestDto(BigDecimal amount, int duration) {
        this.amount = amount;
        this.duration = duration;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
