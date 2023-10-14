package org.ilya.mongoproject.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ilya.mongoproject.model.entities.Customer;
import org.ilya.mongoproject.repository.CustomerRepository;
import org.ilya.mongoproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    //TODO может ли сервис возвращать optional? или же ему всегда нужно возвращать конкретную сущность
    public Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email).orElseThrow(() ->
                new NoSuchElementException("No such customer with email " + email));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllWithLimit(Integer page) {
        return findAll().stream().skip(20L * page)
                .limit(20L)
                .toList();
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such customer with id " + id)
        );
    }

    @Override
    public Customer addNewCustomer(Customer customer) {
        customer.setId(ObjectId.get().toString());
        CompletableFuture.runAsync(() -> customerRepository.save(customer));
        return customer;
    }

    @Override
    public Customer editExistingCustomer(Customer customer) {
        Customer existingCustomer = findById(customer.getId());
        if (customer.getEmail() != null) {
            existingCustomer.setEmail(customer.getEmail());
        }
        if (customer.getSurname() != null) {
            existingCustomer.setSurname(customer.getSurname());
        }
        if (customer.getPhoneNumber() != null) {
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        }
        if (customer.getName() != null) {
            existingCustomer.setName(customer.getName());
        }
        CompletableFuture.runAsync(() -> {
           customerRepository.save(existingCustomer);
           log.info("Successfully updated customer " + customer);
        });
        return existingCustomer;
    }

    @Override
    public void deleteExistingCustomerById(String id) {
        try{
            customerRepository.deleteById(id);
        } catch (Exception e){
            throw new NoSuchElementException("No such customer with id " + id);
        }
    }
}
