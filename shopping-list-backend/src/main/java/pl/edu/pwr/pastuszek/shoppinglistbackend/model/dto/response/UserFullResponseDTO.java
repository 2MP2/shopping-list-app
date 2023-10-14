package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import lombok.Data;

@Data
public class UserFullResponseDTO {
    private String name;
    private String surname;
    private String number;
    private String email;
}
