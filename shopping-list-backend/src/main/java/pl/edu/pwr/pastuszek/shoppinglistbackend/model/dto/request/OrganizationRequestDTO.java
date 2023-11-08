package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationRequestDTO {
    @NotBlank(message = "Organization name shouldn't be blank")
    @Size(min = 3, max = 255, message = "Wrong Organization name")
    private String name;
}
