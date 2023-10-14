package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ProductStatus;

import java.util.UUID;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Product name shouldn't be blank")
    @Size(max = 255, message = "Product name is too long")
    private String name;
    @Size(min = 1, message = "Quantity must be at least 1")
    private int quantity;
    @NotNull(message = "Product status shouldn't be null")
    private ProductStatus status;
    @NotNull(message = "Shopping list ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of shopping list ID")
    private UUID shoppingListId;
}
