package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrganizationRequestDTO {
    @NotBlank(message = "Organization name shouldn't be blank")
    @Max(value = 255, message = "Organization name is too long")
    private String name;
}
