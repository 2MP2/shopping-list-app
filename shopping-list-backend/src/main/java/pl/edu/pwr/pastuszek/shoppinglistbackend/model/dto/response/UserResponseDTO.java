package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import pl.edu.pwr.pastuszek.shoppinglistbackend.validation.Views;

import java.util.UUID;


@Data
public class UserResponseDTO {
    @JsonView(Views.Public.class)
    private UUID id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Public.class)
    private String surname;
    @JsonView(Views.Internal.class)
    private String number;
    @JsonView(Views.Internal.class)
    private String email;
}
