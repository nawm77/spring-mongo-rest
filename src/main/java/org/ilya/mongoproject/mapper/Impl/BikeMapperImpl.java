package org.ilya.mongoproject.mapper.Impl;

import org.ilya.mongoproject.mapper.BikeMapper;
import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.model.dto.response.BikeResponseDTO;
import org.ilya.mongoproject.model.entities.Bike;
import org.springframework.stereotype.Service;

@Service
public class BikeMapperImpl implements BikeMapper {

    @Override
    public BikeResponseDTO toDTO(Bike bike) {
        return BikeResponseDTO.builder()
                .name(bike.getName())
                .type(bike.getType())
                .owner(bike.getOwner())
                .pricePerHour(bike.getPricePerHour())
                .id(bike.getId())
                .build();
    }

    @Override
    public Bike toBike(BikeRequestDTO bikeRequestDTO) {
        return Bike.builder()
                .pricePerHour(bikeRequestDTO.getPricePerHour())
                .type(bikeRequestDTO.getType())
                .name(bikeRequestDTO.getName())
                .owner(bikeRequestDTO.getOwner())
                .id(bikeRequestDTO.getId())
                .build();
    }
}
