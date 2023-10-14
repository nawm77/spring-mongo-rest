package org.ilya.mongoproject.mapper.Impl;

import org.ilya.mongoproject.mapper.CustomerMapper;
import org.ilya.mongoproject.mapper.RentMapper;
import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
import org.ilya.mongoproject.model.dto.response.RentResponseDTO;
import org.ilya.mongoproject.model.entities.Rent;
import org.ilya.mongoproject.service.BikeService;
import org.ilya.mongoproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentMapperImpl implements RentMapper {
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;
    private final BikeService bikeService;

    @Autowired
    public RentMapperImpl(CustomerMapper customerMapper, CustomerService customerService, BikeService bikeService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
        this.bikeService = bikeService;
    }

    public RentResponseDTO toDTO(Rent rent){
        return RentResponseDTO.builder()
                .id(rent.getId())
                .bike(rent.getBike())
                .dateTime(rent.getDay())
                .customerResponseDTO(customerMapper.toDTO(rent.getCustomer()))
                .build();
    }

    public Rent toRent(RentRequestDTO dto){
        return Rent.builder()
                .id(dto.getId())
                .day(dto.getDateTime())
                .customer((customerService.findByEmail(dto.getEmail())))
                .bike(bikeService.findById(dto.getBikeId()))
                .build();
    }
}
