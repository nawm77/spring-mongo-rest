package org.ilya.mongoproject.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BikeResponseDTO {
    private String name;
    private String owner;
    private String type;
    private Integer pricePerHour;
}
