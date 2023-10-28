package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class InvitationRequestDTO {
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of organization ID")
    private UUID organizationId;
    @Email(message = "Invalid email format")
    private String userEmail;
    @NotNull(message = "User number shouldn,t be null")
    @Pattern(regexp = "^\\d{9}$", message = "User phone number wrong pattern")
    private String userNumber;
}
