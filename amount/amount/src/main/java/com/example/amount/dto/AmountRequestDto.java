package com.example.amount.dto;

import com.example.amount.validation.Common;
import com.example.amount.validation.Deposit;
import com.example.amount.validation.UpdateDeposit;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AmountRequestDto {
    @NotNull(groups = Deposit.class)
   @DecimalMin(value = "10",groups = Common.class)
    @DecimalMax(value = "100000",groups = Common.class)
    private BigDecimal amount;

    @NotNull(groups = Deposit.class)
    @Min(value = 1,groups = Common.class)
    @Max(value = 12,groups = Common.class)
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
