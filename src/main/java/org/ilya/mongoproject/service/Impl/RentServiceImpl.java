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
import java.util.Optional;
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
                .limit(20L)
                .toList();
    }

    @Override
    public RentResponseDTO addNewRent(RentRequestDTO rentRequestDTO) {
        CompletableFuture<Bike> findBikeFuture = CompletableFuture.supplyAsync(() ->
                bikeService.findById(rentRequestDTO.getBikeId()));
        CompletableFuture<Customer> findCustomerFuture = CompletableFuture.supplyAsync(() ->
                customerService.findByEmail(rentRequestDTO.getEmail()));
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(findBikeFuture, findCustomerFuture);
        combinedFuture.join();
        Bike foundBike = findBikeFuture.join();
        Customer foundCustomer = findCustomerFuture.join();
        Rent r = new Rent(rentRequestDTO.getDateTime(), foundBike, foundCustomer);
        CompletableFuture.runAsync(() -> rentRepository.save(r));
        return RentResponseDTO.builder()
                .bike(foundBike)
                .dateTime(rentRequestDTO.getDateTime())
                .build();
    }

    @Override
    public RentResponseDTO findRentById(String id) {
        Optional<Rent> rent = rentRepository.findRentById(id);
        if(rent.isPresent()) {
            return RentResponseDTO.builder()
                    .dateTime(rent.get().getDay())
                    .bike(rent.get().getBike())
                    .customer(rent.get().getCustomer())
                    .build();
        } else{
            throw new NoSuchElementException("No such rent " + id);
        }
    }
}
