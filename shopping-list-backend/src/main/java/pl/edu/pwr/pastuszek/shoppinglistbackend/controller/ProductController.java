package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.ProductService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ProductRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ProductResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDTO> getProductList() {
        return this.productService.list();
    }

    @GetMapping("{id}")
    public ProductResponseDTO getProductById(@PathVariable("id") UUID id) {
        return this.productService.getOne(id);
    }

    @PostMapping
    public ProductResponseDTO addUser(@RequestBody ProductRequestDTO productRequestDTO) {
        return this.productService.add(productRequestDTO);
    }

    @PutMapping("{id}")
    public ProductResponseDTO updateUser(@PathVariable("id") UUID id, @RequestBody ProductRequestDTO productRequestDTO){
        return this.productService.update(id, productRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteProductById(@PathVariable("id") UUID id){
        productService.delete(id);
    }

    @PutMapping("billToProducts/{billId}")
    public void addBillToProducts(@PathVariable("billId") UUID billId, @RequestBody Set<UUID> products){
        this.productService.addBillAndPURCHASEDToProductsByIds(billId, products);
    }
}
