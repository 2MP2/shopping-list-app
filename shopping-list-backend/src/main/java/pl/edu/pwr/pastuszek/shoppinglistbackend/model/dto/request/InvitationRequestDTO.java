package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class InvitationRequestDTO {
    @NotNull(message = "Expiration date shouldn't be null")
    @Future(message = "Expiration date should be in the future")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Timestamp expirationDate;
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of organization ID")
    private UUID organizationId;
    @Email(message = "Invalid email format")
    private String userEmail;
    @NotNull(message = "User number shouldn,t be null")
    @Pattern(regexp = "^\\d{9}$", message = "User phone number wrong pattern")
    private String userNumber;
}
