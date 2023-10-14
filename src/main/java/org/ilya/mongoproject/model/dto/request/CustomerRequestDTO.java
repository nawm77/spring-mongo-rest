package org.ilya.mongoproject.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequestDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
}
