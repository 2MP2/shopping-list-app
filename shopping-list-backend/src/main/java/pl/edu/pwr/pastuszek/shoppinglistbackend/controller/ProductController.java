package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.ProductService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getProductList() {
        return this.productService.list();
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable("id") UUID id) {
        return this.productService.getOne(id);
    }

    @PostMapping
    public Product addUser(@RequestBody Product product) {
        return this.productService.add(product);
    }

    @PutMapping("{id}")
    public Product updateUser(@PathVariable("id") UUID id, @RequestBody Product product){
        return this.productService.update(id, product);
    }

    @DeleteMapping("{id}")
    public void deleteProductById(@PathVariable("id") UUID id){
        productService.delete(id);
    }
}
