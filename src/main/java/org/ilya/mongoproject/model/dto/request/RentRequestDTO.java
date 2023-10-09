package org.ilya.mongoproject.model.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
public class RentRequestDTO {
    private LocalDateTime dateTime;
    private String bikeId;
    private String email;
}
