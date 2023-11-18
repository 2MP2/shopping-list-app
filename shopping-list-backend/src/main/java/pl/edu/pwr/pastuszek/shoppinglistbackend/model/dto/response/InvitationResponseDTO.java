package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class InvitationResponseDTO {
    private UUID id;
    private UUID organizationId;
    private String organizationName;
    private String userName;
    private String userSurname;
}
