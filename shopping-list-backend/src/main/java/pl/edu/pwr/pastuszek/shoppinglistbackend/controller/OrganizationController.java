package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.OrganizationService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("organization")
@AllArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping
    public List<Organization> getUserList() {
        return this.organizationService.list();
    }

    @GetMapping("{id}")
    public Organization getOrganizationById(@PathVariable("id") UUID id) {
        return this.organizationService.getOne(id);
    }

    @PostMapping
    public Organization addUser(@RequestBody Organization organization) {
        return this.organizationService.add(organization);
    }

    @PutMapping("{id}")
    public Organization updateUser(@PathVariable("id") UUID id, @RequestBody Organization organization){
        return this.organizationService.update(id, organization);
    }

    @DeleteMapping("{id}")
    public void deleteOrganizationById(@PathVariable("id") UUID id){
        organizationService.delete(id);
    }
}
