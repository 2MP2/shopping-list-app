package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrganizationResponseDTO {
    private UUID id;
    private String name;
    private List<ShoppingListLightResponseDTO> shoppingLists;
}
