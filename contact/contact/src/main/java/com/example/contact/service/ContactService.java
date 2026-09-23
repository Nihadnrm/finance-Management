package com.example.contact.service;

import com.example.contact.dto.ContactRequestDto;
import com.example.contact.dto.ContactResponseDto;
import com.example.contact.entity.Contact;
import com.example.contact.mapper.ContactMapper;
import com.example.contact.repository.ContactRepository;
import com.example.contact.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private ContactRepository repo;
    private JwtService jwtService;
    private ContactMapper contactMapper;

    public ContactService(ContactMapper contactMapper, JwtService jwtService, ContactRepository repo) {
        this.contactMapper = contactMapper;
        this.jwtService = jwtService;
        this.repo = repo;
    }

    public ContactResponseDto makeContact(String token, ContactRequestDto dto){
        String userName=jwtService.extractUserName(token.substring(7));
        Long referenceId=jwtService.extractReferenceId(token.substring(7));
        Contact contact=contactMapper.toEntity(dto);
        contact.setUserName(userName);
        contact.setUserId(referenceId);
        Contact save=repo.save(contact);
        return  contactMapper.toDto(save);

    }
}
