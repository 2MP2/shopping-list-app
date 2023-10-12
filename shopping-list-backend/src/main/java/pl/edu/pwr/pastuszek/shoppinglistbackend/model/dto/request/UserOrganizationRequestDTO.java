package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import lombok.Data;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganizationStatus;

import java.util.UUID;

@Data
public class UserOrganizationRequestDTO {
    private UserOrganizationStatus status;
    private UUID userId;
    private UUID organizationId;
}
