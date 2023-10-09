package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.entities.Bike;

import java.util.List;
import java.util.Optional;

public interface BikeService {
    List<Bike> findAll();
    Optional<Bike> findById(String id);
}
