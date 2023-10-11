package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ShoppingListResponseDTO {
    UUID id;
    String name;
    List<ProductResponseDTO> products;
}
