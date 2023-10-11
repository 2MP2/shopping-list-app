package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserOrganizationResponseDTO {
    private UUID id;
    private UserResponseDTO user;
    private UUID organizationId;
    private String organizationName;
}
