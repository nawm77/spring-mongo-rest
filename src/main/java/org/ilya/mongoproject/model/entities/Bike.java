package org.ilya.mongoproject.model.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bikes")
@Data
public class Bike {
    private String id;
    private String name;
    private String type;
    private Integer pricePerHour;
    private String owner;
}
