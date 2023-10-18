package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.ShoppingListService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ShoppingListRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ShoppingListResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForLogin;

import java.util.UUID;

@ForLogin
@RestController
@RequestMapping("shopping-list")
@AllArgsConstructor
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    @GetMapping
    public Page<ShoppingListResponseDTO> getShoppingListList(Pageable pageable) {
        return this.shoppingListService.list(pageable);
    }

    @GetMapping("{id}")
    public ShoppingListResponseDTO getShoppingListById(@PathVariable("id") UUID id) {
        return this.shoppingListService.getOne(id);
    }

    @PostMapping
    public ShoppingListResponseDTO addShoppingList(@Valid @RequestBody ShoppingListRequestDTO shoppingListRequestDTO) {
        return this.shoppingListService.add(shoppingListRequestDTO);
    }

    @PutMapping("{id}")
    public ShoppingListResponseDTO updateShoppingList(@Valid @PathVariable("id") UUID id, @RequestBody ShoppingListRequestDTO shoppingListRequestDTO){
        return this.shoppingListService.update(id, shoppingListRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteShoppingListById(@PathVariable("id") UUID id){
        shoppingListService.delete(id);
    }
}
