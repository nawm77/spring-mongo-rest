package org.ilya.mongoproject.model.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bikes")
@Data
@Builder
public class Bike {
    private String id;
    private String name;
    private String type;
    private Integer pricePerHour;
    private String owner;
    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", owner='" + owner + '\'' +
                '}';
    }
}
