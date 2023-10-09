package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.entities.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByEmail(String email);
}
