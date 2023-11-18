package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pwr.pastuszek.shoppinglistbackend.validation.Views;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
