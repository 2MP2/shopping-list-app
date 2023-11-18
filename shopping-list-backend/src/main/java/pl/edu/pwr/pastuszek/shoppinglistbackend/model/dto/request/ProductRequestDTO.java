package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Product name shouldn't be blank")
    @Size(max = 128, message = "Product name is too long")
    private String name;
    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;
    @NotNull(message = "Product status shouldn't be null")
    private boolean purchased;
    @NotNull(message = "Shopping list ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of shopping list ID")
    private String shoppingListId;
}
