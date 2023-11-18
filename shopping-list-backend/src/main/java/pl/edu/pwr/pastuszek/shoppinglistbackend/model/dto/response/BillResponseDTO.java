package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
public class BillResponseDTO {
    private UUID id;
    private BigDecimal amount;
    private String shop;
    private String comment;
    private Timestamp updateTime;
    private List<ProductResponseDTO> products;
    private UserResponseDTO user;
}
