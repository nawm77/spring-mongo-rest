package org.ilya.mongoproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.ilya.mongoproject.exceptionHandler.ApiException;
import org.ilya.mongoproject.mapper.CustomerMapper;
import org.ilya.mongoproject.model.dto.request.CustomerRequestDTO;
import org.ilya.mongoproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static org.ilya.mongoproject.model.constants.ApiConstants.CUSTOMER_API_V1_PATH;

@RestController
@RequestMapping(CUSTOMER_API_V1_PATH)
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomer(@RequestParam(name = "page", required = false) Integer page) {
        try {
            return page == null ?
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            customerService.findAll().stream()
                                    .map(customerMapper::toDTO)
                                    .toList()
                    )
                    :
                    ResponseEntity.status(HttpStatus.FOUND).body(
                            customerService.findAllWithLimit(page).stream()
                                    .map(customerMapper::toDTO)
                                    .toList()
                    );
        } catch (Exception e) {
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiException.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message(e.getLocalizedMessage())
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    customerMapper.toDTO(customerService.findById(id))
            );
        } catch (Exception e) {
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .message(e.getLocalizedMessage())
                            .httpStatus(HttpStatus.NOT_FOUND)
            );
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                customerMapper.toDTO(customerService.addNewCustomer(customerMapper.toCustomer(customerRequestDTO)))
        );
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editExistingCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    customerMapper.toDTO(customerService.editExistingCustomer(customerMapper.toCustomer(customerRequestDTO)))
            );
        } catch (NoSuchElementException e) {
            log.warn("Exception: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .message(e.getLocalizedMessage())
                            .httpStatus(HttpStatus.NOT_FOUND)
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("id") String id) {
        try{
            customerService.deleteExistingCustomerById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiException.builder()
                            .message(e.getLocalizedMessage())
                            .httpStatus(HttpStatus.NOT_FOUND)
            );
        }
    }
}
