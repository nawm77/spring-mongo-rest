package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.model.dto.response.BikeResponseDTO;
import org.ilya.mongoproject.model.entities.Bike;

import java.util.List;

public interface BikeService {
    List<Bike> findAll();
    Bike findById(String id);
    List<Bike> findAllWithLimit(Integer count);
    BikeResponseDTO addNewBike(BikeRequestDTO bike);

    BikeResponseDTO editExistingBike(BikeRequestDTO bikeRequestDTO);
    void deleteExistingBike(String id);
}
