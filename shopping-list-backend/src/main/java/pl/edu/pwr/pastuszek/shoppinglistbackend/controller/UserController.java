package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.UserService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUserList() {
        return this.userService.list();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return this.userService.getOne(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return this.userService.add(user);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable("id") UUID id, @RequestBody User user){
        return this.userService.update(id, user);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable("id") UUID id){
        userService.delete(id);
    }

}
