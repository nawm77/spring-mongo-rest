package org.ilya.mongoproject.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.ilya.mongoproject.model.entities.Bike;
import org.ilya.mongoproject.repository.BikeRepository;
import org.ilya.mongoproject.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
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
    public List<Bike> findAllWithLimit(Integer count) {
        return findAll().stream().skip(20L * count)
                .limit(20L)
                .toList();
    }
    @Override
    public Bike findById(String id) {
        Optional<Bike> b = bikeRepository.findBikeById(id);
        if(b.isPresent()){
            return b.get();
        } else{
            throw new NoSuchElementException("No such bike with id " + id);
        }
    }
}
