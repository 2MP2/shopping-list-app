package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.InvitationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.InvitationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.InvitationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForAdmin;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForLoggedIn;

import java.util.Map;
import java.util.UUID;

@ForLoggedIn
@RestController
@RequestMapping("invitation")
@AllArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping
    public Page<InvitationResponseDTO> getInvitationList(@RequestParam(required = false) Map<String, String> params, Pageable pageable) {
        return this.invitationService.list(params, pageable);
    }

    @GetMapping("{id}")
    public InvitationResponseDTO getInvitationById(@PathVariable("id") UUID id) {
        return this.invitationService.getOne(id);
    }

    @PostMapping
    public InvitationResponseDTO addInvitation(@Valid @RequestBody InvitationRequestDTO invitationRequestDTO) {
        return this.invitationService.add(invitationRequestDTO);
    }

    @ForAdmin
    @PutMapping("{id}")
    public InvitationResponseDTO updateInvitation(@Valid @PathVariable("id") UUID id, @RequestBody InvitationRequestDTO invitationRequestDTO){
        return this.invitationService.update(id, invitationRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteInvitationById(@PathVariable("id") UUID id){
        invitationService.delete(id);
    }

    @PostMapping("accept/{id}")
    public void acceptInvitation(@PathVariable("id") UUID id, @RequestParam("isAccepted") boolean isAccepted){
        invitationService.acceptInvitation(id, isAccepted);
    }
}
