package org.ilya.mongoproject.model.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bikes")
@Data
@Builder
public class Bike {
    @Id
    private String id;
    @Indexed
    private String name;
    @Indexed
    private String type;
    @Indexed
    private Integer pricePerHour;
    private String owner;
}
