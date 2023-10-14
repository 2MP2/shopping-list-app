package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.UserOrganizationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserOrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserOrganizationResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user-organization")
@AllArgsConstructor
public class UserOrganizationController {
    private final UserOrganizationService userOrganizationService;

    @GetMapping
    public List<UserOrganizationResponseDTO> getUserOrganizationList() {
        return this.userOrganizationService.list();
    }

    @GetMapping("{id}")
    public UserOrganizationResponseDTO getUserOrganizationById(@PathVariable("id") UUID id) {
        return this.userOrganizationService.getOne(id);
    }

    @PostMapping
    public UserOrganizationResponseDTO addUserOrganization(@Valid @RequestBody UserOrganizationRequestDTO userOrganizationRequestDTO) {
        return this.userOrganizationService.add(userOrganizationRequestDTO);
    }

    @PutMapping("{id}")
    public UserOrganizationResponseDTO updateUserOrganization(@Valid @PathVariable("id") UUID id, @RequestBody UserOrganizationRequestDTO userOrganizationRequestDTO){
        return this.userOrganizationService.update(id, userOrganizationRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteUserOrganizationById(@PathVariable("id") UUID id){
        userOrganizationService.delete(id);
    }

}