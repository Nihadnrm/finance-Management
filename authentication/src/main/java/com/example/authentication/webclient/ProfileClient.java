package com.example.authentication.webclient;

import com.example.authentication.dto.ProfileRequestDto;
import com.example.authentication.dto.ProfileResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class ProfileClient {
    private final WebClient webClient;

    public ProfileClient(WebClient webClient) {
        this.webClient = webClient;
    }


    @CircuitBreaker(name = "profileServer",fallbackMethod = "profileFallback")
    public ProfileResponseDto createProfile(ProfileRequestDto dto){


      return   webClient.post().uri("http://localhost:8082/profile/create").bodyValue(dto).retrieve()
                .bodyToMono(ProfileResponseDto.class)
                .doOnSuccess(value->{
                    System.out.println(value.getProfileName());
                    })
                .doOnError(e->{
                    System.out.println("can no hit on profile server");
                    System.out.println(e.getMessage());
                })
                .block();
    }

    public ProfileResponseDto profileFallback(ProfileRequestDto dto,Exception ex){
        return new ProfileResponseDto(00L,"no name","no email",00L,"no address","no imageUrl", BigDecimal.ZERO);
    }
}
