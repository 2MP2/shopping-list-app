package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.UserService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserFullResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserResponseDTO;

import java.util.UUID;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Page<UserResponseDTO> getUserList(Pageable pageable) {
        return this.userService.list(pageable);
    }

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

    //Probably change UserResponseDTO to new dto in this 3 endpoints
    @GetMapping("login")
    public UserResponseDTO login(){
        return null; //TODO
    }

    @PostMapping("register")
    public UserResponseDTO register(){
        return null; //TODO
    }

    @GetMapping("info/{id}")
    public UserFullResponseDTO getFullInfo(@PathVariable("id") UUID id){
        return this.userService.getFullUserDto(id);
    }

}
