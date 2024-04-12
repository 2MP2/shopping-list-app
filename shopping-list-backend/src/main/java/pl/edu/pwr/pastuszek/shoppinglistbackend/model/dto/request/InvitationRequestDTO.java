package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InvitationRequestDTO {
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of organization ID")
    private String organizationId;
    @Size(max = 64, message = "User e-mail should have max 64 characters")
    @Email(message = "Invalid email format")
    private String userEmail;
}
