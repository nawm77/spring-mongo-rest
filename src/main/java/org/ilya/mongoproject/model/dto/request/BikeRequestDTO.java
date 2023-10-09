package org.ilya.mongoproject.model.dto.request;

import lombok.Data;

@Data
public class BikeRequestDTO {
    private String name;
    private String type;
    private Integer pricePerHour;
    private String owner;
    private String id;
}
