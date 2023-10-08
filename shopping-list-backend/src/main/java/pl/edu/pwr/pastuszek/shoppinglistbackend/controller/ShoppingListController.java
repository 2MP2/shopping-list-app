package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.ShoppingListService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("shopping-list")
@AllArgsConstructor
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    @GetMapping
    public List<ShoppingList> getShoppingListList() {
        return this.shoppingListService.list();
    }

    @GetMapping("{id}")
    public ShoppingList getShoppingListById(@PathVariable("id") UUID id) {
        return this.shoppingListService.getOne(id);
    }

    @PostMapping
    public ShoppingList addShoppingList(@RequestBody ShoppingList shoppingList) {
        return this.shoppingListService.add(shoppingList);
    }

    @PutMapping("{id}")
    public ShoppingList updateShoppingList(@PathVariable("id") UUID id, @RequestBody ShoppingList shoppingList){
        return this.shoppingListService.update(id, shoppingList);
    }

    @DeleteMapping("{id}")
    public void deleteShoppingListById(@PathVariable("id") UUID id){
        shoppingListService.delete(id);
    }
}
