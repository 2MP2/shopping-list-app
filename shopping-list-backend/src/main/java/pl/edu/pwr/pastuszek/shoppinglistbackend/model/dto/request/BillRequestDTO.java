package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BillRequestDTO {
    @NotNull(message = "Amount shouldn't be null")
    @Min(value = 0, message = "Amount must be at least 0")
    private int amount;
    @NotBlank(message = "Shop shouldn't be blank")
    @Size(max = 32, message = "Shop name is too long, max 255 characters")
    private String shop;
    @Size(max = 255, message = "Comment is too long, max 255 characters")
    private String comment;
    @NotNull(message = "Shopping list ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of shopping list ID")
    private String shoppingListId;
    @NotNull(message = "User ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of user ID")
    private String userId;
}
