package org.ilya.mongoproject.mapper.Impl;

import org.ilya.mongoproject.mapper.CustomerMapper;
import org.ilya.mongoproject.model.dto.request.CustomerRequestDTO;
import org.ilya.mongoproject.model.dto.response.CustomerResponseDTO;
import org.ilya.mongoproject.model.entities.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapperImpl implements CustomerMapper {
    @Override
    public CustomerResponseDTO toDTO(Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .surname(customer.getSurname())
                .name(customer.getName())
                .build();
    }

    @Override
    public Customer toCustomer(CustomerRequestDTO customerRequestDTO) {
        return Customer.builder()
                .surname(customerRequestDTO.getSurname())
                .name(customerRequestDTO.getName())
                .email(customerRequestDTO.getEmail())
                .phoneNumber(customerRequestDTO.getPhoneNumber())
                .id(customerRequestDTO.getId())
                .build();
    }
}
