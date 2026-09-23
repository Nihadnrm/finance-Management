package com.example.contact.controller;

import com.example.contact.dto.ContactRequestDto;
import com.example.contact.dto.ContactResponseDto;
import com.example.contact.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping("")
    public ContactResponseDto makeContact(@RequestHeader("Authorization")String token, @Valid@RequestBody ContactRequestDto dto){
        return  service.makeContact(token,dto);
    }
}
