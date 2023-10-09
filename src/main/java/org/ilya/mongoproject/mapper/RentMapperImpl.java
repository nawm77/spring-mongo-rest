package org.ilya.mongoproject.mapper;

import org.ilya.mongoproject.model.dto.response.RentResponseDTO;
import org.ilya.mongoproject.model.entities.Rent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentMapperImpl implements RentMapper{
    private final ModelMapper modelMapper;

    @Autowired
    public RentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RentResponseDTO toDTO(Rent rent){
        return modelMapper.map(rent, RentResponseDTO.class);
    }

    public Rent toRent(RentResponseDTO dto){
        return modelMapper.map(dto, Rent.class);
    }
}
