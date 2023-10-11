package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ProductStatus;

import java.util.UUID;

public record ProductResponseDTO (
        UUID id,
        String name,
        int quantity,
        ProductStatus status
//    , Bill bill , ShoppingList shoppingList // add on Product validation group
){}
