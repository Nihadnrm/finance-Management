package com.example.amount.webclient;

import com.example.amount.dto.ProfileResponseDto;
import com.example.amount.dto.ProfileTotalUpdateDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class ProfileClient {
    private WebClient webClient;

    public ProfileClient(WebClient webClient) {
        this.webClient = webClient;
    }

 @CircuitBreaker(name = "profileServer",fallbackMethod = "profileFallback")
public ProfileResponseDto updateTotal(String token, ProfileTotalUpdateDto dto){
      return   webClient.put().uri("http://localhost:8082/profile/updatetotal")
                .header("Authorization",token)
                .bodyValue(dto).retrieve().bodyToMono(ProfileResponseDto.class)
                .doOnSuccess(value->{
                    System.out.println(value.getTotalAmount());
                })
                .doOnError(e->{
                    System.out.println("can not hot on profile server");
                    System.out.println(e.getMessage());
                })
                .block();
}
public ProfileResponseDto profileFallback(String token, ProfileTotalUpdateDto dto, Exception ex){
    return new ProfileResponseDto(00L,"no name","no email",00L,"no address","no imageUrl", BigDecimal.ZERO);

}

}
