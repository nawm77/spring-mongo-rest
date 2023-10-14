package org.ilya.mongoproject.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseDTO {
    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String surname;
}
