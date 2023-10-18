package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.InvitationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.InvitationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.InvitationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForLogin;

import java.util.UUID;

@ForLogin
@RestController
@RequestMapping("invitation")
@AllArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping
    public Page<InvitationResponseDTO> getInvitationList(Pageable pageable) {
        return this.invitationService.list(pageable);
    }

    @GetMapping("{id}")
    public InvitationResponseDTO getInvitationById(@PathVariable("id") UUID id) {
        return this.invitationService.getOne(id);
    }

    @PostMapping
    public InvitationResponseDTO addInvitation(@Valid @RequestBody InvitationRequestDTO invitationRequestDTO) {
        return this.invitationService.add(invitationRequestDTO);
    }

    @PutMapping("{id}")
    public InvitationResponseDTO updateInvitation(@Valid @PathVariable("id") UUID id, @RequestBody InvitationRequestDTO invitationRequestDTO){
        return this.invitationService.update(id, invitationRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteInvitationById(@PathVariable("id") UUID id){
        invitationService.delete(id);
    }

    @PostMapping("accept/{id}")
    public void acceptInvitation(@PathVariable("id") UUID id){
        invitationService.acceptToTrueAndAddUserOrganization(id);
    }
}
