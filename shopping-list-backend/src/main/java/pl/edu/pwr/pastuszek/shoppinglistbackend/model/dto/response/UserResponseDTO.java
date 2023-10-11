package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import java.util.UUID;


public record UserResponseDTO (
    UUID id,
    String name,
    String surname
){}
