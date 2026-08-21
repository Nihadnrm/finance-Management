package com.example.amount.controller;

import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.service.AmountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AmountController {
    private final AmountService amountService;

    public AmountController(AmountService amountService) {
        this.amountService = amountService;
    }

    @PostMapping("/amount")
    public AmountResponseDto depositAmount(@Valid @RequestHeader("Authorization")String token, @RequestBody AmountRequestDto dto){
        return amountService.depositAmount(token,dto);
    }
    @GetMapping("/amount")
    public List<AmountResponseDto>showAllDeposits(@RequestHeader("Authorization")String token){
        return amountService.showAllDeposits(token);
    }

    @PutMapping("/amount/{id}")
    public AmountResponseDto updateAmount(@RequestHeader("Authorization")String token,@RequestBody AmountRequestDto dto,@PathVariable Long id){
        return amountService.updateAmount(token,dto,id);
    }

}
