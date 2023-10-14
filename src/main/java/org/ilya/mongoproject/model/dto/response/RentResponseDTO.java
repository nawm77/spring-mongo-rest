package org.ilya.mongoproject.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RentResponseDTO {
    private String id;
    private LocalDateTime dateTime;
    private BikeResponseDTO bike;
    private CustomerResponseDTO customer;

}
