package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.InvitationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("invitation")
@AllArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping
    public List<Invitation> getInvitationList() {
        return this.invitationService.list();
    }

    @GetMapping("{id}")
    public Invitation getInvitationById(@PathVariable("id") UUID id) {
        return this.invitationService.getOne(id);
    }

    @PostMapping
    public Invitation addInvitation(@RequestBody Invitation invitation) {
        return this.invitationService.add(invitation);
    }

    @PutMapping("{id}")
    public Invitation updateInvitation(@PathVariable("id") UUID id, @RequestBody Invitation invitation){
        return this.invitationService.update(id, invitation);
    }

    @DeleteMapping("{id}")
    public void deleteInvitationById(@PathVariable("id") UUID id){
        invitationService.delete(id);
    }
}
