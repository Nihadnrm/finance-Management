package com.example.amount.controller;

import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.dto.TotalAmountForUserDto;
import com.example.amount.service.AmountService;
import com.example.amount.validation.Deposit;
import com.example.amount.validation.UpdateDeposit;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public Page<AmountResponseDto> showAllDeposits(@RequestHeader("Authorization")String token, @RequestParam(defaultValue = "0")  int page,@RequestParam(defaultValue = "5") int size){
        return amountService.showAllDeposits(token,page,size);
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
   @GetMapping("/amount/range")
    public Page<AmountResponseDto>getByAmount(@RequestHeader("Authorization")String token, @RequestParam BigDecimal min,@RequestParam BigDecimal max,@RequestParam int duration,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5")int size){
        return amountService.getByAmount(token,min,max,duration,page,size);
   }

   @GetMapping("/amount/userid/{id}")
    public Page<AmountResponseDto>getByUserId(@RequestHeader("Authorization")String token,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5")int size){
        return amountService.getByUserId(token,page,size);
   }
   @GetMapping("/amount/custotal")
   public TotalAmountForUserDto customerTotal(@RequestHeader("Authorization")String token){
        return amountService.customerTotal(token);
   }
}
