package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class InvitationResponseDTO {
    private UUID id;
    private Timestamp expirationDate;
    private UUID organizationId;
    private String organizationName;
    private String userName;
    private String userSurname;
}
