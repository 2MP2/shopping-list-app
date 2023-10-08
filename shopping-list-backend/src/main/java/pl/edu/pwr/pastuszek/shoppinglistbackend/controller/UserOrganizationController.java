package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.UserOrganizationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user-organization")
@AllArgsConstructor
public class UserOrganizationController {
    private final UserOrganizationService userOrganizationService;

    @GetMapping
    public List<UserOrganization> getUserOrganizationList() {
        return this.userOrganizationService.list();
    }

    @GetMapping("{id}")
    public UserOrganization getUserOrganizationById(@PathVariable("id") UUID id) {
        return this.userOrganizationService.getOne(id);
    }

    @PostMapping
    public UserOrganization addUserOrganization(@RequestBody UserOrganization userOrganization) {
        return this.userOrganizationService.add(userOrganization);
    }

    @PutMapping("{id}")
    public UserOrganization updateUserOrganization(@PathVariable("id") UUID id, @RequestBody UserOrganization userOrganization){
        return this.userOrganizationService.update(id, userOrganization);
    }

    @DeleteMapping("{id}")
    public void deleteUserOrganizationById(@PathVariable("id") UUID id){
        userOrganizationService.delete(id);
    }

}