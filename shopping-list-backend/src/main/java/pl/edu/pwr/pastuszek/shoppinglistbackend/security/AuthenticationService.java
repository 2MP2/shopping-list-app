package pl.edu.pwr.pastuszek.shoppinglistbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom.RegistrationException;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.UserDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.AuthenticationRequest;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.AuthenticationResponse;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;

    public AuthenticationResponse register(UserRequestDTO user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RegistrationException("Email is already taken");
        }

        if(userRepository.existsByNumber(user.getNumber())){
            throw new RegistrationException("Phone number is already taken");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userDTOMapper.convertDtoToFullEntity(user);
        userRepository.save(newUser);
        var jwt = jwtService.generateToken(newUser);
        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
        );

        User user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(()-> new IllegalStateException(
                        "user with id: " + authenticationRequest.getEmail() + " dose not exists"
                ));
        String token = jwtService.generateToken(Map.of("id", user.getId()), user);
        return new AuthenticationResponse(token);
    }
}
