package com.example.profile.httpClient;

import com.example.profile.dto.TotalAmountForUserDto;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AmountHttpClient {

    public TotalAmountForUserDto getAmount(String token)throws  Exception {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8081/amount/custotal")).header("Authorization", token).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), TotalAmountForUserDto.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("can not access amount service");
        }


    }

}
