package com.example.amount.controller;

import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.service.AmountService;
import com.example.amount.validation.Deposit;
import com.example.amount.validation.UpdateDeposit;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AmountController {
    private final AmountService amountService;

    public AmountController(AmountService amountService) {
        this.amountService = amountService;
    }

    @PostMapping("/amount")
    public AmountResponseDto depositAmount( @RequestHeader("Authorization")String token,@Validated(Deposit.class)@RequestBody AmountRequestDto dto){
        return amountService.depositAmount(token,dto);
    }
    @GetMapping("/amount")
    public List<AmountResponseDto>showAllDeposits(@RequestHeader("Authorization")String token){
        return amountService.showAllDeposits(token);
    }
    @GetMapping("/amount/{id}")
    public AmountResponseDto getAmountById(@RequestHeader("Authorization")String token,@PathVariable Long id){
        return amountService.getAmountById(token,id);
    }

    @PutMapping("/amount/{id}")
    public AmountResponseDto updateAmount(@RequestHeader("Authorization")String token, @Validated(UpdateDeposit.class)@RequestBody AmountRequestDto dto, @PathVariable Long id){
        return amountService.updateAmount(token,dto,id);
    }
    @DeleteMapping("/amount/{id}")
    public String deleteAmount(@RequestHeader("Authorization")String token,@PathVariable long id){
        return amountService.deleteAmount(token,id);
    }

}
