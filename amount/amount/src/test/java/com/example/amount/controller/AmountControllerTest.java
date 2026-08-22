package com.example.amount.controller;


import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.security.JwtService;
import com.example.amount.service.AmountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AmountController.class)
public class AmountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AmountService amountService;
    @MockitoBean
    private JwtService jwtService;

    @Test
    public void depositAmount() throws  Exception{

        AmountResponseDto responseDto=new AmountResponseDto();


        when(amountService.depositAmount(anyString(),any(AmountRequestDto.class))).thenReturn(responseDto);

     mockMvc.perform(post("/amount").header("Authorization","Bearer-test-token").contentType(MediaType.APPLICATION_JSON).content("""
             {  "amount"
                 "duration":2         
              }""")).andExpect(status().isBadRequest());
    }

    @Test
    public void showAllDeposits()throws Exception{
        List<AmountResponseDto>responseListDto=new ArrayList<>();

        when(amountService.showAllDeposits(anyString())).thenReturn(responseListDto);
        mockMvc.perform(get("/amount").header("Authorization","Bearer-test-token"))
                .andExpect(status().isOk());
    }


    @Test
    public void updateAmount()throws  Exception{
        AmountResponseDto responseDto=new AmountResponseDto();

        when(amountService.updateAmount(anyString(),any(AmountRequestDto.class),anyLong()))
                .thenReturn(responseDto);

        mockMvc.perform(put("/amount/1").header("Authorization","Bearer-test-token")
                .contentType(MediaType.APPLICATION_JSON).content("""
                        {
                        "amount":20000,
                        "duration":3
                        }
                        
                        """)).andExpect(status().isOk());

    }
}
