package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
import org.ilya.mongoproject.model.dto.response.RentResponseDTO;
import org.ilya.mongoproject.model.entities.Rent;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface RentService {
    List<Rent> findAll();
    List<Rent> findAllWithLimit(Integer count);
    RentResponseDTO addNewRent(RentRequestDTO rentRequestDTO) throws ExecutionException, InterruptedException;
    RentResponseDTO findRentById(String id);
}
