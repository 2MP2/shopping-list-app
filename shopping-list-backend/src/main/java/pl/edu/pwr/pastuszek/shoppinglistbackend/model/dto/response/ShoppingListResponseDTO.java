package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ShoppingListResponseDTO {
    UUID id;
    String name;
}
