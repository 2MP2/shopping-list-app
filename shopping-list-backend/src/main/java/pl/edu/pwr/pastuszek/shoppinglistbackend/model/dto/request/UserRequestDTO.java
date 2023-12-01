package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank(message = "User name shouldn't be blank")
    @Size(max = 32, message = "User password should have max 32 characters")
    private String name;
    @Size(max = 32, message = "User surname should have max 32 characters")
    @NotBlank(message = "User surname shouldn't be blank")
    private String surname;
    @NotNull(message = "User number shouldn,t be null")
    @Pattern(regexp = "^\\d{9}$", message = "User phone number wrong pattern")
    private String number;
    @Size(max = 64, message = "User e-mail should have max 64 characters")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "User password shouldn't be blank")
    @Size(min = 5, message = "User password should have at least 5 characters")
    @Size(max = 255, message = "User password should have max 256 characters")
    private String password;
}
