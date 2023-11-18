package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.AuthenticationRequest;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.AuthenticationResponse;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.AuthenticationService;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody UserRequestDTO user) {
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }
}
