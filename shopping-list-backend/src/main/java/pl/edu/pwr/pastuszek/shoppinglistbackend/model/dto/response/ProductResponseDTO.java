package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private int quantity;
    private boolean purchased;
    private UUID billId;
}
