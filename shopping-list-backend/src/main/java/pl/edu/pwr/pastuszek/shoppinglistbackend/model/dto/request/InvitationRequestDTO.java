package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class InvitationRequestDTO {
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of organization ID")
    private String organizationId;
    @Email(message = "Invalid email format")
    private String userEmail;
}
