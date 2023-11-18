package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganizationStatus;

import java.util.UUID;

@Data
public class UserOrganizationResponseDTO {
    private UUID id;
    private UserOrganizationStatus status;
    private UserResponseDTO user;
    private UUID organizationId;
    private String organizationName;
}
