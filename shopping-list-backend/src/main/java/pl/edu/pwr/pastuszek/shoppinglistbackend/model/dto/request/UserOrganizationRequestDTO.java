package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganizationStatus;

@Data
public class UserOrganizationRequestDTO {
    @NotNull(message = "User organization status shouldn't be null")
    private UserOrganizationStatus status;
    @NotNull(message = "User ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of user ID")
    private String userId;
    @NotNull(message = "Organization ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of organization ID")
    private String organizationId;
}
