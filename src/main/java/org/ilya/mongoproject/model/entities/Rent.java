package org.ilya.mongoproject.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rent {
    private String id;
    private LocalDateTime day;
    private Bike bike;
    private Customer customer;
    public Rent(LocalDateTime day, Bike bike, Customer customer) {
        this.day = day;
        this.bike = bike;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id='" + id + '\'' +
                ", day='" + day + '\'' +
                ", bike=" + bike.getName() +
                ", type=" + bike.getType() +
                //TODO сделать tostring у байка и у кастомера
                ", owner=" + bike.getOwner() +
                ", price=" + bike.getPricePerHour() +
                ", customer email=" + customer.getEmail() +
                '}';
    }
}
