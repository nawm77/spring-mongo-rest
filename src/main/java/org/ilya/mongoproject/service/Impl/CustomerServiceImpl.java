package org.ilya.mongoproject.service.Impl;

import org.ilya.mongoproject.model.entities.Customer;
import org.ilya.mongoproject.repository.CustomerRepository;
import org.ilya.mongoproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }
}
