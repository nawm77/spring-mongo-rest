package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.entities.Customer;

public interface CustomerService {
    Customer findByEmail(String email);
}
