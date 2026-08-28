package com.example.amount.service;

import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.entity.Amount;
import com.example.amount.mapper.AmountMapper;
import com.example.amount.repository.AmountRepository;
import com.example.amount.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class AmountService {
    private  final AmountRepository repo;
    private final JwtService jwtService;
    private  final AmountMapper amountMapper;

    public AmountService(AmountMapper amountMapper, AmountRepository repo, JwtService jwtService) {
        this.amountMapper = amountMapper;
        this.repo = repo;
        this.jwtService = jwtService;
    }


    @CacheEvict(value = "amount",key = "'all'")
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

    @Cacheable(value = "amount",key = "'all'")
    public List<AmountResponseDto> showAllDeposits(String token){
        List<Amount>list=repo.findAll();
        return amountMapper.toListDto(list);

    }

    @Cacheable(value = "amount",key = "#id")
    public AmountResponseDto getAmountById(String token,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not found"));
        return amountMapper.toDTO(amount);
    }

//    @CachePut(value = "amount",key = "#id")
    @Caching(put = @CachePut(value = "amount",key = "#id"),evict = @CacheEvict(value = "amount",key ="'all'"))

    public AmountResponseDto updateAmount(String token,AmountRequestDto dto,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not found"));
        if(dto.getAmount()!=null){
            amount.setAmount(amount.getAmount().add(dto.getAmount()));
        }
        amount.setDuration(amount.getDuration()+dto.getDuration());
        amount.setTakeBackDate(amount.getTakeBackDate().plusMonths(dto.getDuration()));
        Amount update= repo.save(amount);
        return amountMapper.toDTO(update);

    }
//    @CacheEvict(value = "amount",key = "#id")
    @Caching(evict ={ @CacheEvict(value = "amount",key = "#id"),@CacheEvict(value = "amount",key = "'all'")})
    public String deleteAmount(String token,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not fount"));
        repo.delete(amount);
        return amount.getId()+" "+"id's" +" "+ "amount is deleted";
    }
}
