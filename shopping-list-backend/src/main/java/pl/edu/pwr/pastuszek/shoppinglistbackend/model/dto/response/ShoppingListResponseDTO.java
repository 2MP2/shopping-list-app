package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;

import java.util.List;
import java.util.UUID;

public record ShoppingListResponseDTO (
    UUID id,
    String name,
    List<Product> products  //add this in validation group only on ShoppingList endpoint
){}
