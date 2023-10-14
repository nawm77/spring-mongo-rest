package org.ilya.mongoproject.model.dto.response;

import lombok.Builder;
import lombok.Data;
import org.ilya.mongoproject.model.entities.Bike;

import java.time.LocalDateTime;

@Data
@Builder
public class RentResponseDTO {
    private String id;
    private LocalDateTime dateTime;
    private Bike bike;
    private CustomerResponseDTO customerResponseDTO;

}
