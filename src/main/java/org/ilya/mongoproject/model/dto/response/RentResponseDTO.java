package org.ilya.mongoproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ilya.mongoproject.model.entities.Bike;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentResponseDTO {
    private LocalDateTime dateTime;
    private Bike bike;
}
