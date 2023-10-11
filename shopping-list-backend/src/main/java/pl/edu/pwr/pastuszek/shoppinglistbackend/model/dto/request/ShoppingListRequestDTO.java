package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ShoppingListRequestDTO {
    private String name;
    private UUID organizationId;
}
