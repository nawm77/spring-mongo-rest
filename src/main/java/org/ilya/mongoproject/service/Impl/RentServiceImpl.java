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
import java.util.concurrent.*;

@Service
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final BikeService bikeService;
    private final CustomerService customerService;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

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
        CompletableFuture<Optional<Bike>> findBikeFuture = CompletableFuture.supplyAsync(() ->
                bikeService.findById(rentRequestDTO.getBikeId()));
        CompletableFuture<Optional<Customer>> findCustomerFuture = CompletableFuture.supplyAsync(() ->
                customerService.findByEmail(rentRequestDTO.getEmail()));
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(findBikeFuture, findCustomerFuture);
        combinedFuture.join();
        Optional<Bike> foundBike = findBikeFuture.join();
        Optional<Customer> foundCustomer = findCustomerFuture.join();
        if (validateBikeAndCustomer(foundBike, foundCustomer)) {
            Rent r = new Rent(rentRequestDTO.getDateTime(), foundBike.get(), foundCustomer.get());
            CompletableFuture.runAsync(() -> rentRepository.save(r));
            return RentResponseDTO.builder()
                    .bike(foundBike.get())
                    .dateTime(rentRequestDTO.getDateTime())
                    .build();
        } else{
            throw new NoSuchElementException("No such bike " + rentRequestDTO.getBikeId());
        }
    }

    private boolean validateBikeAndCustomer(Optional<Bike> bike, Optional<Customer> customer) {
        return bike.isPresent() && customer.isPresent();
    }
}
