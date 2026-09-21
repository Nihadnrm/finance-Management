package com.example.amount.service;

import com.example.amount.dto.*;
import com.example.amount.entity.Amount;
import com.example.amount.mapper.AmountMapper;
import com.example.amount.repository.AmountRepository;
import com.example.amount.security.JwtService;
import com.example.amount.specification.AmountSpecification;
import com.example.amount.webclient.ProfileClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Slf4j
@Service
public class AmountService {
    private  final AmountRepository repo;
    private final JwtService jwtService;
    private  final AmountMapper amountMapper;
    private ProfileClient profileClient;


    public AmountService(AmountMapper amountMapper, AmountRepository repo, JwtService jwtService, ProfileClient profileClient) {
        this.amountMapper = amountMapper;
        this.repo = repo;
        this.jwtService = jwtService;
        this.profileClient = profileClient;
    }

    @CacheEvict(value = "amount",allEntries = true)
    public AmountResponseDto depositAmount(String token, AmountRequestDto dto) {

        String userName = jwtService.extractUserName(token.substring(7));
        Long referenceId=jwtService.extractReferenceId(token.substring(7));
        log.info("Request amount: {}", dto.getAmount());
        log.info("Request duration: {}", dto.getDuration());

        if(repo.existsByUserId(referenceId)){
            throw  new RuntimeException("amount already added,make update now");
        }

        Amount amount = amountMapper.toEntity(dto);
        amount.setUserId(referenceId);

        log.info("BEFORE SAVE - amount: {}", amount.getAmount());
        log.info("BEFORE SAVE - duration: {}", amount.getDuration());

        Amount save = repo.save(amount);

        log.info("AFTER SAVE - amount: {}", save.getAmount());
        log.info("AFTER SAVE - duration: {}", save.getDuration());

        ProfileTotalUpdateDto profileTotalUpdateDto=new ProfileTotalUpdateDto();
        profileTotalUpdateDto.setTotalAmount(save.getAmount());

        ProfileResponseDto profile=profileClient.updateTotal(token,profileTotalUpdateDto);

        return amountMapper.toDTO(save);
    }

    @Cacheable(value = "amount",key = "'all-'+ #page+ '-' + #size")
    public Page<AmountResponseDto> showAllDeposits(String token,int page,int size){
        Pageable pageable=PageRequest.of(page,size);

        Page<Amount> pageList=repo.findAll(pageable);
        return amountMapper.toListDto(pageList);

    }

    @Cacheable(value = "amount",key = "#id")
    public AmountResponseDto getAmountById(String token,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not found"));
        return amountMapper.toDTO(amount);
    }

//    @CachePut(value = "amount",key = "#id")
    @Caching(put = @CachePut(value = "amount",key = "#id"),evict = @CacheEvict(value = "amount",allEntries = true))
    public AmountResponseDto updateAmount(String token,AmountRequestDto dto,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not found"));
        if(dto.getAmount()!=null){
            amount.setAmount(amount.getAmount().add(dto.getAmount()));
        }
        amount.setDuration(amount.getDuration()+dto.getDuration());
        amount.setTakeBackDate(amount.getTakeBackDate().plusMonths(dto.getDuration()));
        Amount update= repo.save(amount);
        ProfileTotalUpdateDto profileTotalUpdateDto=new ProfileTotalUpdateDto();
        profileTotalUpdateDto.setTotalAmount(update.getAmount());

        ProfileResponseDto profile=profileClient.updateTotal(token,profileTotalUpdateDto);
        return amountMapper.toDTO(update);

    }
//    @CacheEvict(value = "amount",key = "#id")
    @Caching(evict ={ @CacheEvict(value = "amount",key = "#id"),@CacheEvict(value = "amount",allEntries = true)})
    public String deleteAmount(String token,Long id){
        Amount amount=repo.findById(id).orElseThrow(()->new RuntimeException("amount not fount"));
        repo.delete(amount);
        return amount.getId()+" "+"id's" +" "+ "amount is deleted";
    }

    public Page<AmountResponseDto>getByAmount(String token, BigDecimal min,BigDecimal max,int duration,int page,int size){
       Pageable pageable=PageRequest.of(page,size);
        Specification<Amount>spe= AmountSpecification.priceBetween(min,max).and(AmountSpecification.duration(duration));
       Page<Amount>list=repo.findAll(spe,pageable);
       return amountMapper.toListDto(list);

    }

    public Page<AmountResponseDto>getByUserId(String token,int page,int size){
        Long referenceId=jwtService.extractReferenceId(token.substring(7));
        Pageable pageable=PageRequest.of(page,size);
        Page<Amount>pages=repo.findByUserId(referenceId,pageable);
        return amountMapper.toListDto(pages);
    }

    public TotalAmountForUserDto customerTotal(String token){
        Long referenceId=jwtService.extractReferenceId(token.substring(7));
        Object[] data=repo.findTotalByUserId(referenceId);
        return amountMapper.toTotalAmount(data);


    }

}
