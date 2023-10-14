package org.ilya.mongoproject.mapper;

import org.ilya.mongoproject.model.dto.request.CustomerRequestDTO;
import org.ilya.mongoproject.model.dto.response.CustomerResponseDTO;
import org.ilya.mongoproject.model.entities.Customer;

public interface CustomerMapper {
    CustomerResponseDTO toDTO(Customer customer);
    Customer toCustomer(CustomerRequestDTO customerRequestDTO);
}
