package org.ilya.mongoproject.service.Impl;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public Rent findRentById(String id) {
        Optional<Rent> rent = rentRepository.findRentById(id);
        if(rent.isPresent()) {
            return rent.get();
        } else{
            throw new NoSuchElementException("No such rent " + id);
        }
    }

    @Override
    public RentResponseDTO editExistingRent(RentRequestDTO rentRequestDTO) {
        Rent existingRent = findRentById(rentRequestDTO.getId());
        CompletableFuture.runAsync(() ->{
            if (rentRequestDTO.getDateTime() != null){
                existingRent.setDay(rentRequestDTO.getDateTime());
            }
            if (rentRequestDTO.getBikeId() != null){
                existingRent.setBike(bikeService.findById(rentRequestDTO.getBikeId()));
            }
            if (rentRequestDTO.getEmail() != null){
                existingRent.setCustomer(customerService.findByEmail(rentRequestDTO.getEmail()));
            }
        });
        log.info("Successfully edited rent" + rentRequestDTO);
        return RentResponseDTO.builder()
                .dateTime(existingRent.getDay())
                .customer(existingRent.getCustomer())
                .bike(existingRent.getBike())
                .build();
    }

    @Override
    public void deleteRentById(String id) {
        try{
            rentRepository.deleteById(id);
        } catch (Exception e){
            throw new NoSuchElementException("No such rent with id " + id);
        }
    }
}
