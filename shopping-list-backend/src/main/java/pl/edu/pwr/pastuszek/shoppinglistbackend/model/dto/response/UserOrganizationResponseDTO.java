package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import java.util.UUID;

public record UserOrganizationResponseDTO (
    UUID id,
    UserResponseDTO userResponseDTO,
    OrganizationResponseDTO organizationResponseDTO
){}
