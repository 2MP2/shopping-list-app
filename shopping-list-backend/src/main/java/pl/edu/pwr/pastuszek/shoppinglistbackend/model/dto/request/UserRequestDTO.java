package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank(message = "User name shouldn't be blank")
    private String name;
    @NotBlank(message = "User surname shouldn't be blank")
    private String surname;
    @NotNull(message = "User number shouldn,t be null")
    @Pattern(regexp = "^\\d{9}$", message = "User phone number wrong pattern")
    private String number;
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "User login shouldn't be blank")
    @Size(min = 5, message = "User login should have at least 5 characters")
    private String login;
    @NotBlank(message = "User password shouldn't be blank")
    @Size(min = 5, message = "User password should have at least 5 characters")
    private String password;
}
