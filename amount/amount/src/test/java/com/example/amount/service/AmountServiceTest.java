package com.example.amount.service;

import com.example.amount.dto.AmountRequestDto;
import com.example.amount.dto.AmountResponseDto;
import com.example.amount.entity.Amount;
import com.example.amount.mapper.AmountMapper;
import com.example.amount.repository.AmountRepository;
import com.example.amount.security.JwtService;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AmountServiceTest {

    @Mock
    private AmountRepository repo;

    @Mock
    private AmountMapper amountMapper;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AmountService service;





    @BeforeAll
    public static void beforeAll() {
        System.out.println("TESTING STARTED");
    }

    // Common data
    String token;
    AmountRequestDto dto;
    Amount amount;
    AmountResponseDto responseDto;

    @BeforeEach
    public void beforeEach() {

        System.out.println("PREPARING TEST");

        // Common data for every test
        token = "Bearer abc123";

        dto = new AmountRequestDto();
        dto.setDuration(12);

        amount = new Amount();
        amount.setDuration(12);

        responseDto = new AmountResponseDto();
    }


    @Test
    public void depositAmount() {

        // Arrange

        Amount savedAmount = new Amount();


        when(jwtService.extractUserName(anyString()))
                .thenReturn("nihad");

        when(repo.existsByDuration(dto.getDuration()))
                .thenReturn(false);

        when(amountMapper.toEntity(dto))
                .thenReturn(amount);

        when(repo.save(amount))
                .thenReturn(savedAmount);

        when(amountMapper.toDTO(savedAmount))
                .thenReturn(responseDto);


        // Act

        AmountResponseDto result =
                service.depositAmount(token, dto);


        // Assert

        assertNotNull(result);
        assertEquals(responseDto, result);
    }


    @Test
    public void depositAmount_DurationAlreadyExists() {

        // Arrange

        when(jwtService.extractUserName(anyString()))
                .thenReturn("nihad");

        when(repo.existsByDuration(dto.getDuration()))
                .thenReturn(true);


        // Act + Assert

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.depositAmount(token, dto)
        );
        // Assert

        assertEquals(
                "This duration is already taken",
                exception.getMessage()
        );
    }


    @Test
    public void showAllDeposits() {

        // Arrange

        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        List<Amount> amountList = new ArrayList<>();

        Page<Amount> amountPage =
                new PageImpl<>(
                        amountList,
                        pageable,
                        amountList.size()
                );

        List<AmountResponseDto> responseList = new ArrayList<>();

        Page<AmountResponseDto> responsePage =
                new PageImpl<>(
                        responseList,
                        pageable,
                        responseList.size()
                );

        when(repo.findAll(pageable))
                .thenReturn(amountPage);

        when(amountMapper.toListDto(amountPage))
                .thenReturn(responsePage);


        // Act

        Page<AmountResponseDto> result =
                service.showAllDeposits(token, page, size);


        // Assert

        assertNotNull(result);
        assertEquals(responsePage, result);
    }


    @Test
    public void updateAmount() {

        // Arrange

        Long id = 1L;

        Amount existingAmount = new Amount();
        existingAmount.setDuration(12);
        existingAmount.setTakeBackDate(LocalDateTime.now());


        Amount updatedAmount = existingAmount;


        when(repo.findById(id))
                .thenReturn(Optional.of(existingAmount));


        when(repo.save(existingAmount))
                .thenReturn(updatedAmount);

        when(amountMapper.toDTO(updatedAmount))
                .thenReturn(responseDto);


        // Act

        AmountResponseDto result =
                service.updateAmount(token, dto, id);


        // Assert

        assertNotNull(result);
        assertEquals(responseDto, result);
    }


    @AfterEach
    public void afterEach() {

        System.out.println("Test finished");
    }


    @AfterAll
    public static void afterAll() {

        System.out.println("All AmountService tests finished");
    }
}