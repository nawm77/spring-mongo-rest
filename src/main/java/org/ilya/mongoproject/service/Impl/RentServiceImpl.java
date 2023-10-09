package org.ilya.mongoproject.service.Impl;

import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
import org.ilya.mongoproject.model.dto.response.RentResponseDTO;
import org.ilya.mongoproject.model.entities.Bike;
import org.ilya.mongoproject.model.entities.Customer;
import org.ilya.mongoproject.model.entities.Rent;
import org.ilya.mongoproject.repository.RentRepository;
import org.ilya.mongoproject.service.BikeService;
import org.ilya.mongoproject.service.CustomerService;
import org.ilya.mongoproject.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final BikeService bikeService;
    private final CustomerService customerService;

    @Autowired
    public RentServiceImpl(RentRepository rentRepository, BikeService bikeService, CustomerService customerService) {
        this.rentRepository = rentRepository;
        this.bikeService = bikeService;
        this.customerService = customerService;
    }

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public List<Rent> findAllWithLimit(Integer count) {
        return findAll().stream().skip(20L * count)
                .limit(20L * (count + 1))
                .toList();
    }

    @Override
    public RentResponseDTO addNewRent(RentRequestDTO rentRequestDTO) {
        if (validateRent(rentRequestDTO)) {
            Bike b = bikeService.findById(rentRequestDTO.getBikeId()).get();
            Customer c = customerService.findByEmail(rentRequestDTO.getEmail()).get();
            Rent r = new Rent(rentRequestDTO.getDateTime(), b, c);
            CompletableFuture.runAsync(() -> rentRepository.save(r));
            return RentResponseDTO.builder()
                    .bike(b)
                    .dateTime(rentRequestDTO.getDateTime())
                    .build();
        } else{
            throw new NoSuchElementException("No such bike");
        }
    }

    private boolean validateRent(RentRequestDTO rentRequestDTO){
        return bikeService.findById(rentRequestDTO.getBikeId()).isPresent() && customerService.findByEmail(rentRequestDTO.getEmail()).isPresent();
    }
}
