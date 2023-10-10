package org.ilya.mongoproject.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.ilya.mongoproject.model.dto.request.BikeRequestDTO;
import org.ilya.mongoproject.model.dto.response.BikeResponseDTO;
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
    public BikeResponseDTO addNewBike(BikeRequestDTO bike) {
        CompletableFuture.runAsync(() -> {
            Bike b = Bike.builder()
                    .owner(bike.getOwner())
                    .name(bike.getName())
                    .type(bike.getType())
                    .pricePerHour(bike.getPricePerHour())
                    .build();
            log.info("Successfully saved bike " + bikeRepository.save(b));
        });
        return BikeResponseDTO.builder()
                .owner(bike.getOwner())
                .name(bike.getName())
                .type(bike.getType())
                .pricePerHour(bike.getPricePerHour())
                .build();
    }

    @Override
    public BikeResponseDTO editExistingBike(BikeRequestDTO bikeRequestDTO) {
        Bike existingBike = findById(bikeRequestDTO.getId());
        CompletableFuture.runAsync(() -> {
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
            bikeRepository.save(existingBike);
        });
        log.info("Successfully updated bike " + existingBike);
        return BikeResponseDTO.builder()
                .name(existingBike.getName())
                .owner(existingBike.getOwner())
                .type(existingBike.getType())
                .pricePerHour(existingBike.getPricePerHour())
                .build();
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
