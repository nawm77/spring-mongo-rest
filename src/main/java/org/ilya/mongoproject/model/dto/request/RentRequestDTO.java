package org.ilya.mongoproject.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentRequestDTO {
    private String id;
    private LocalDateTime dateTime;
    private String bikeId;
    private String email;
}
