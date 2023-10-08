package org.ilya.mongoproject.service.Impl;

import org.ilya.mongoproject.model.entities.Rent;
import org.ilya.mongoproject.repository.RentRepository;
import org.ilya.mongoproject.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;

    @Autowired
    public RentServiceImpl(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }
}
