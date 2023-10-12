package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String surname;
    private String number;
    private String email;
    private String login;
    private String password;
}
