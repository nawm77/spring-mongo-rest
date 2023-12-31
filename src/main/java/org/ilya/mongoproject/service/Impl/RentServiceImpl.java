package org.ilya.mongoproject.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ilya.mongoproject.model.dto.request.RentRequestDTO;
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
    public Rent addNewRent(RentRequestDTO rentRequestDTO) {
        ObjectId id = ObjectId.get();
        CompletableFuture<Bike> findBikeFuture = CompletableFuture.supplyAsync(() ->
                bikeService.findById(rentRequestDTO.getBikeId()));
        CompletableFuture<Customer> findCustomerFuture = CompletableFuture.supplyAsync(() ->
                customerService.findByEmail(rentRequestDTO.getEmail()));
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(findBikeFuture, findCustomerFuture);
        combinedFuture.join();
        Bike foundBike = findBikeFuture.join();
        Customer foundCustomer = findCustomerFuture.join();
        Rent r = new Rent(id.toString(), rentRequestDTO.getDateTime(), foundBike, foundCustomer);
        CompletableFuture.runAsync(() -> rentRepository.save(r));
        return r;
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
    public Rent editExistingRent(RentRequestDTO rentRequestDTO) {
        Rent existingRent = findRentById(rentRequestDTO.getId());
        if (rentRequestDTO.getDateTime() != null){
            existingRent.setDay(rentRequestDTO.getDateTime());
        }
        CompletableFuture<Bike> getFutureBike = CompletableFuture.supplyAsync(() ->
                bikeService.findById(rentRequestDTO.getBikeId()));
        CompletableFuture<Customer> getFutureCustomer = CompletableFuture.supplyAsync(() ->
                customerService.findByEmail(rentRequestDTO.getEmail()));
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(getFutureCustomer, getFutureCustomer);
        combinedFuture.join();
        if (rentRequestDTO.getBikeId() != null){
            existingRent.setBike(getFutureBike.join());
        }
        if (rentRequestDTO.getEmail() != null){
            existingRent.setCustomer(getFutureCustomer.join());
        }
        log.info("Successfully edited rent" + rentRequestDTO);
        CompletableFuture.runAsync(() -> rentRepository.save(existingRent));
        return existingRent;
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
