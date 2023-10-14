package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
import org.ilya.mongoproject.model.dto.response.RentResponseDTO;
import org.ilya.mongoproject.model.entities.Rent;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface RentService {
    List<Rent> findAll();
    List<Rent> findAllWithLimit(Integer count);
    Rent addNewRent(RentRequestDTO rentRequestDTO) throws ExecutionException, InterruptedException;
    Rent findRentById(String id);
    Rent editExistingRent(RentRequestDTO rentRequestDTO);
    void deleteRentById(String id);
}
