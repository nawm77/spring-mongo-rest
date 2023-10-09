package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.entities.Bike;

import java.util.List;

public interface BikeService {
    List<Bike> findAll();
    Bike findById(String id);
    List<Bike> findAllWithLimit(Integer count);
}
