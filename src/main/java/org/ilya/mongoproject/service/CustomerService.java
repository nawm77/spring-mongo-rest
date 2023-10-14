package org.ilya.mongoproject.service;

import org.ilya.mongoproject.model.entities.Customer;

import java.util.List;

public interface CustomerService {
    Customer findByEmail(String email);
    List<Customer> findAll();
    List<Customer> findAllWithLimit(Integer page);
    Customer findById(String id);
    Customer addNewCustomer(Customer customer);
    Customer editExistingCustomer(Customer customer);
    void deleteExistingCustomerById(String id);
}
