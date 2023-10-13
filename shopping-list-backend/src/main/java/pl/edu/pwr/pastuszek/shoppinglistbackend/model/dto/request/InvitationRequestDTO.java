package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class InvitationRequestDTO {
    private Timestamp expirationDate;
    private UUID organizationId;
    private String userEmail;
    private String userNumber;
}
