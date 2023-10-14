package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.model.entities.Bike;
import org.ilya.mongoproject.model.exception.FilterArgsException;

import java.util.List;

public interface BikeService {
    List<Bike> findAll();
    Bike findById(String id);
    List<Bike> findAllWithLimit(Integer count);
    Bike addNewBike(BikeRequestDTO bike);

    Bike editExistingBike(Bike bike);
    void deleteExistingBikeById(String id);
    List<Bike> findBikeByPriceLimitAndSortDesc(Integer startPrice, Integer endPrice) throws FilterArgsException;
}
