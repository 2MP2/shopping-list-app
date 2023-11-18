package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillRequestDTO {
    @NotNull(message = "Amount shouldn't be null")
    @NotNull(message = "Amount cannot be null")
    @Digits(integer = 10, fraction = 2, message = "Invalid format for amount")
    private BigDecimal amount;
    @NotBlank(message = "Shop shouldn't be blank")
    @Size(max = 32, message = "Shop name is too long, max 32 characters")
    private String shop;
    @Size(max = 256, message = "Comment is too long, max 256 characters")
    private String comment;
    @NotNull(message = "Shopping list ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of shopping list ID")
    private String shoppingListId;
    @NotNull(message = "User ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of user ID")
    private String userId;
}
