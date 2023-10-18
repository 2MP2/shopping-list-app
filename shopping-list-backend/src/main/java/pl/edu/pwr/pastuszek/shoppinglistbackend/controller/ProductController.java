package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.ProductService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ProductRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ProductResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForLogin;

import java.util.Set;
import java.util.UUID;

@ForLogin
@RestController
@RequestMapping("product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductResponseDTO> getProductList(Pageable pageable) {
        return this.productService.list(pageable);
    }

    @GetMapping("{id}")
    public ProductResponseDTO getProductById(@PathVariable("id") UUID id) {
        return this.productService.getOne(id);
    }

    @PostMapping
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return this.productService.add(productRequestDTO);
    }

    @PutMapping("{id}")
    public ProductResponseDTO updateProduct(@Valid @PathVariable("id") UUID id, @RequestBody ProductRequestDTO productRequestDTO){
        return this.productService.update(id, productRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteProductById(@PathVariable("id") UUID id){
        productService.delete(id);
    }

    @PutMapping("bill-to-products/{billId}")
    public void addBillToProducts(@PathVariable("billId") UUID billId, @RequestBody Set<UUID> products){
        this.productService.addBillAndPURCHASEDToProductsByIds(billId, products);
    }
}
