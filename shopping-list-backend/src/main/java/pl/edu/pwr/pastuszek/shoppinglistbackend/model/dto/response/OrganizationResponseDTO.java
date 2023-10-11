package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import java.util.UUID;

public record OrganizationResponseDTO (
        UUID id,
        String name
//    , List<ShoppingList> shoppingLists //uncomment when add validation group on Organization endpoint
){}
