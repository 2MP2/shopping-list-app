package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.OrganizationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.OrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.OrganizationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForAdmin;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForLogin;

import java.util.UUID;

@ForLogin
@RestController
@RequestMapping("organization")
@AllArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping
    public Page<OrganizationResponseDTO> getOrganizationList(Pageable pageable) {
        return this.organizationService.list(pageable);
    }

    @GetMapping("{id}")
    public OrganizationResponseDTO getOrganizationById(@PathVariable("id") UUID id) {
        return this.organizationService.getOne(id);
    }

    @ForAdmin
    @PostMapping
    public OrganizationResponseDTO addOrganization(@Valid @RequestBody OrganizationRequestDTO organizationRequestDTO) {
        return this.organizationService.add(organizationRequestDTO);
    }

    @PutMapping("{id}")
    public OrganizationResponseDTO updateOrganization(@Valid @PathVariable("id") UUID id, @RequestBody OrganizationRequestDTO organizationRequestDTO){
        return this.organizationService.update(id, organizationRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteOrganizationById(@PathVariable("id") UUID id){
        organizationService.delete(id);
    }
}
