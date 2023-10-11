package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;

import java.util.List;
import java.util.UUID;

public record BillResponseDTO (
    UUID id,
    int amount,
    String shop,
    String comment,
    List<Product> products //add validation Bill group
){}
