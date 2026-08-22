package com.example.amount.service;

import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.entity.Amount;
import com.example.amount.mapper.AmountMapper;
import com.example.amount.repository.AmountRepository;
import com.example.amount.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.authorization.ConditionalAuthorizationManager.when;

@Slf4j
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


    public AmountResponseDto depositAmount(String token, AmountRequestDto dto) {

        String userName = jwtService.extractUserName(token.substring(7));

        log.info("Request amount: {}", dto.getAmount());
        log.info("Request duration: {}", dto.getDuration());

        if (repo.existsByDuration(dto.getDuration())) {
            log.warn("Duration already exists: {}", dto.getDuration());
            throw new RuntimeException("This duration is already taken");
        }

        Amount amount = amountMapper.toEntity(dto);

        log.info("BEFORE SAVE - amount: {}", amount.getAmount());
        log.info("BEFORE SAVE - duration: {}", amount.getDuration());

        Amount save = repo.save(amount);

        log.info("AFTER SAVE - amount: {}", save.getAmount());
        log.info("AFTER SAVE - duration: {}", save.getDuration());

        return amountMapper.toDTO(save);
    }

    public List<AmountResponseDto> showAllDeposits(String token){
        List<Amount>list=repo.findAll();
        return amountMapper.toListDto(list);

    }

    public AmountResponseDto updateAmount(String token,AmountRequestDto dto,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not found"));
        amount.setAmount(amount.getAmount().add(dto.getAmount()));
        amount.setDuration(amount.getDuration()+dto.getDuration());
        amount.setTakeBackDate(amount.getTakeBackDate().plusMonths(dto.getDuration()));
        Amount update= repo.save(amount);
        return amountMapper.toDTO(update);

    }
}
