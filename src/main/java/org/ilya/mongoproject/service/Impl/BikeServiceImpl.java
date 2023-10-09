package org.ilya.mongoproject.service.Impl;

import org.ilya.mongoproject.model.entities.Bike;
import org.ilya.mongoproject.repository.BikeRepository;
import org.ilya.mongoproject.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BikeServiceImpl implements BikeService {
    private final BikeRepository bikeRepository;

    @Autowired
    public BikeServiceImpl(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public List<Bike> findAll() {
        return bikeRepository.findAll();
    }

    @Override
    public Optional<Bike> findById(String id) {
        return bikeRepository.findBikeById(id);
    }
}
