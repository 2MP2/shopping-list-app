package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.InvitationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.InvitationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.InvitationResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("invitation")
@AllArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping
    public List<InvitationResponseDTO> getInvitationList() {
        return this.invitationService.list();
    }

    @GetMapping("{id}")
    public InvitationResponseDTO getInvitationById(@PathVariable("id") UUID id) {
        return this.invitationService.getOne(id);
    }

    @PostMapping
    public InvitationResponseDTO addInvitation(@RequestBody InvitationRequestDTO invitationRequestDTO) {
        return this.invitationService.add(invitationRequestDTO);
    }

    @PutMapping("{id}")
    public InvitationResponseDTO updateInvitation(@PathVariable("id") UUID id, @RequestBody InvitationRequestDTO invitationRequestDTO){
        return this.invitationService.update(id, invitationRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteInvitationById(@PathVariable("id") UUID id){
        invitationService.delete(id);
    }

    @PostMapping(name = "accept", params = "{id}")
    public void acceptInvitation(@PathVariable("id") UUID id){
        //TODO add record in UserOrganization and change accepted to true in invitation
    }
}
