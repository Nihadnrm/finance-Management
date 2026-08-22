package com.example.amount.service;

import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.entity.Amount;
import com.example.amount.mapper.AmountMapper;
import com.example.amount.repository.AmountRepository;
import com.example.amount.security.JwtService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.authorization.ConditionalAuthorizationManager.when;

@Service
public class AmountService {
    private  final AmountRepository repo;
    private  final AmountMapper amountMapper;
    private final JwtService jwtService;

    public AmountService(AmountMapper amountMapper, AmountRepository repo, JwtService jwtService) {
        this.amountMapper = amountMapper;
        this.repo = repo;
        this.jwtService = jwtService;
    }


    public AmountResponseDto depositAmount(String token, AmountRequestDto dto){
        String UserName=jwtService.extractUserName(token.substring(7));
        if(repo.existsByDuration(dto.getDuration())){
            throw new RuntimeException("this duration you already taken take another");
        }
        Amount amount=amountMapper.toEntity(dto);
        Amount save=repo.save(amount);
        return amountMapper.toDTO(save);


    }

    public List<AmountResponseDto> showAllDeposits(String token){
        List<Amount>list=repo.findAll();
        return amountMapper.toListDto(list);

    }

    public AmountResponseDto updateAmount(String token,AmountRequestDto dto,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not found"));
        amountMapper.updateAmount(dto,amount);
        Amount update= repo.save(amount);
        return amountMapper.toDTO(update);

    }
}
