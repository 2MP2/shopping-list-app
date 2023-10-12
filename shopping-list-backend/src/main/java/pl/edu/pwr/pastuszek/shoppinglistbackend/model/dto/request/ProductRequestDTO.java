package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import lombok.Data;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ProductStatus;

@Data
public class ProductRequestDTO {
    private String name;
    private int quantity;
    private ProductStatus status;
    private String shoppingListId;
}
