package com.example.contact.mapper;

import com.example.contact.dto.ContactRequestDto;
import com.example.contact.dto.ContactResponseDto;
import com.example.contact.entity.Contact;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {


    Contact toEntity(ContactRequestDto dto);


    ContactResponseDto toDto(Contact contact);
}
