package org.ilya.mongoproject.mapper;

import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.model.dto.response.BikeResponseDTO;
import org.ilya.mongoproject.model.entities.Bike;

public interface BikeMapper {
    BikeResponseDTO toDTO(Bike bike);
    Bike toBike(BikeRequestDTO bikeRequestDTO);
}
