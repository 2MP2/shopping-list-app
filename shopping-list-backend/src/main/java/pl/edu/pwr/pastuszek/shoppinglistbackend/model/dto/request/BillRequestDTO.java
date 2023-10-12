package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class BillRequestDTO {
    private int amount;
    private String shop;
    private String comment;
    private UUID shoppingListId;
}
