package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class InvitationResponseDTO {
    UUID id;
    Timestamp expirationDate;
    UUID organizationId;
    String organizationName;
}
