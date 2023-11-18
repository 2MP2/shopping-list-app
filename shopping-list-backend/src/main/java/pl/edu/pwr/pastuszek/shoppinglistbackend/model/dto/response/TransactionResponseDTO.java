package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private UserResponseDTO userFrom;
    private UserResponseDTO userTo;
    private BigDecimal amount;
}
