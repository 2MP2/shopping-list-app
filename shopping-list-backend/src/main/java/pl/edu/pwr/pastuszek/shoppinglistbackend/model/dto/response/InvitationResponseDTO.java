package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import java.sql.Timestamp;
import java.util.UUID;

public record InvitationResponseDTO (
    UUID id,
    Timestamp expirationDate,
    OrganizationResponseDTO organizationResponseDTO
){}
