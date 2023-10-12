package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.ShoppingListService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ShoppingListRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ShoppingListResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("shopping-list")
@AllArgsConstructor
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    @GetMapping
    public List<ShoppingListResponseDTO> getShoppingListList() {
        return this.shoppingListService.list();
    }

    @GetMapping("{id}")
    public ShoppingListResponseDTO getShoppingListById(@PathVariable("id") UUID id) {
        return this.shoppingListService.getOne(id);
    }

    @PostMapping
    public ShoppingListResponseDTO addShoppingList(@RequestBody ShoppingListRequestDTO shoppingListRequestDTO) {
        return this.shoppingListService.add(shoppingListRequestDTO);
    }

    @PutMapping("{id}")
    public ShoppingListResponseDTO updateShoppingList(@PathVariable("id") UUID id, @RequestBody ShoppingListRequestDTO shoppingListRequestDTO){
        return this.shoppingListService.update(id, shoppingListRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteShoppingListById(@PathVariable("id") UUID id){
        shoppingListService.delete(id);
    }
}
