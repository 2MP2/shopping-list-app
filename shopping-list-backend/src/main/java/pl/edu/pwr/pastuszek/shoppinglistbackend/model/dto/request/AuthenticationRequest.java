package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @Size(max = 64, message = "User e-mail should have max 64 characters")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "User password shouldn't be blank")
    private String password;
}
