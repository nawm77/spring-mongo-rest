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
        //могу ли я создавать objectid еще до сохранения в монго?
        log.info("Successfully saved bike " + bikeRepository.save(b));
        return b;
    }

    @Override
    public Bike editExistingBike(Bike bike) {
        Bike existingBike = findById(bike.getId());
        if (bike.getName() != null) {
            existingBike.setName(bike.getName());
        }
        if (bike.getType() != null) {
            existingBike.setType(bike.getType());
        }
        if (bike.getPricePerHour() != null) {
            existingBike.setPricePerHour(bike.getPricePerHour());
        }
        if (bike.getOwner() != null) {
            existingBike.setOwner(bike.getOwner());
        }
        CompletableFuture.runAsync(() -> {
            bikeRepository.save(existingBike);
            log.info("Successfully updated bike " + existingBike);
        });
        return existingBike;
    }

    @Override
    public void deleteExistingBikeById(String id) {
        try{
            bikeRepository.deleteById(id);
        } catch (Exception e){
            throw new NoSuchElementException("No such bike with id " + id);
        }
    }

    @Override
    public Bike findById(String id) {
        return bikeRepository.findBikeById(id).orElseThrow(
                () -> new NoSuchElementException("No such bike with id " + id)
        );
    }
}
