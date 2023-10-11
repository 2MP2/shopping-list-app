package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private int quantity;
    private String status;
    private String shoppingListId;
}
