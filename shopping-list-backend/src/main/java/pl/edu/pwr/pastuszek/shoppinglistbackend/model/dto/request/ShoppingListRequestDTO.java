package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShoppingListRequestDTO {
    @NotBlank(message = "Shopping list name shouldn't be blank")
    @Size(max = 255, message = "Shopping list name is too long")
    private String name;
    @NotNull(message = "Organization ID shouldn't be null")
    @org.hibernate.validator.constraints.UUID(message = "Invalid format of organization ID")
    private String organizationId;
}
