package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.UserService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForLogin;
import pl.edu.pwr.pastuszek.shoppinglistbackend.validation.Views;

import java.util.UUID;

@ForLogin
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @JsonView(Views.Public.class)
    @GetMapping
    public Page<UserResponseDTO> getUserList(Pageable pageable) {
        return this.userService.list(pageable);
    }

    @JsonView(Views.Public.class)
    @GetMapping("{id}")
    public UserResponseDTO getUserById(@PathVariable("id") UUID id) {
        return this.userService.getOne(id);
    }

    @PostMapping
    public UserResponseDTO addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return this.userService.add(userRequestDTO);
    }

    @PutMapping("{id}")
    public UserResponseDTO updateUser(@Valid @PathVariable("id") UUID id, @RequestBody UserRequestDTO userRequestDTO){
        return this.userService.update(id, userRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable("id") UUID id){
        userService.delete(id);
    }

    @JsonView(Views.Internal.class)
    @GetMapping("info/{id}")
    public UserResponseDTO getFullInfo(@PathVariable("id") UUID id){
        return this.userService.getOne(id);
    }

}
