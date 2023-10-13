package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BillResponseDTO {
    private UUID id;
    private int amount;
    private String shop;
    private String comment;
    private List<ProductResponseDTO> products;
    private UserResponseDTO owner;
}
