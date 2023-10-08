package org.ilya.mongoproject.model.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rents")
@Data
public class Rent {
    private String id;
    private String day;
    @DBRef
    private Bike bike;
}
