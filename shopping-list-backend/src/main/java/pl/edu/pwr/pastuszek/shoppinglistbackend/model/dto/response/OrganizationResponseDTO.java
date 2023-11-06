package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class OrganizationResponseDTO {
    private UUID id;
    private String name;
}
