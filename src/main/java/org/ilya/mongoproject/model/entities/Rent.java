package org.ilya.mongoproject.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rent {
    @Id
    private String id;
    @Indexed
    private LocalDateTime day;
    private Bike bike;
    @Indexed
    private Customer customer;
}
