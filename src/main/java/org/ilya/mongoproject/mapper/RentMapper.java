package org.ilya.mongoproject.mapper;


import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
import org.ilya.mongoproject.model.dto.response.RentResponseDTO;
import org.ilya.mongoproject.model.entities.Rent;

public interface RentMapper {
    RentResponseDTO toDTO(Rent rent);
    Rent toRent(RentRequestDTO dto);
}
