package org.ilya.mongoproject.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.model.entities.Bike;
import org.ilya.mongoproject.repository.BikeRepository;
import org.ilya.mongoproject.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
    public Bike addNewBike(BikeRequestDTO bikeDTO) {
        //todo check existing bikes
        Bike b = Bike.builder()
                .owner(bikeDTO.getOwner())
                .name(bikeDTO.getName())
                .type(bikeDTO.getType())
                .pricePerHour(bikeDTO.getPricePerHour())
                .build();
        CompletableFuture.runAsync(() -> log.info("Successfully saved bike " + bikeRepository.save(b)));
        return b;
    }

    @Override
    public Bike editExistingBike(BikeRequestDTO bikeRequestDTO) {
        Bike existingBike = findById(bikeRequestDTO.getId());
        if (bikeRequestDTO.getName() != null) {
            existingBike.setName(bikeRequestDTO.getName());
        }
        if (bikeRequestDTO.getType() != null) {
            existingBike.setType(bikeRequestDTO.getType());
        }
        if (bikeRequestDTO.getPricePerHour() != null) {
            existingBike.setPricePerHour(bikeRequestDTO.getPricePerHour());
        }
        if (bikeRequestDTO.getOwner() != null) {
            existingBike.setOwner(bikeRequestDTO.getOwner());
        }
        CompletableFuture.runAsync(() -> bikeRepository.save(existingBike));
        log.info("Successfully updated bike " + existingBike);
        return existingBike;
    }

    @Override
    public void deleteExistingBike(String id) {
        try{
            bikeRepository.deleteById(id);
        } catch (Exception e){
            throw new NoSuchElementException("No such bike with id " + id);
        }
    }

    @Override
    public Bike findById(String id) {
        Optional<Bike> b = bikeRepository.findBikeById(id);
        if(b.isPresent()){
            return b.get();
        } else{
            log.info("No such bike with id " + id);
            throw new NoSuchElementException("No such bike with id " + id);
        }
    }
}
