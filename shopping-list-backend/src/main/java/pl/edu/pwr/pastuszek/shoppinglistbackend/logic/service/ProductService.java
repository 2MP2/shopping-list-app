package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BillRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ProductRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.ProductDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ProductRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ProductResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ProductStatus;

import java.util.Set;
import java.util.UUID;

@Service
public class ProductService extends MappedCrudService<Product, ProductRequestDTO, ProductResponseDTO> {

    private final BillRepository billRepository;
    public ProductService(ProductRepository repository, ProductDTOMapper mapper, BillRepository billRepository) {
        super(repository, LoggerFactory.getLogger(ProductService.class), mapper);
        this.billRepository = billRepository;
    }


    public void addBillAndPURCHASEDToProductsByIds(UUID billId, Set<UUID> products){
        Bill bill = billRepository.findById(billId)
                .orElseThrow(()->{
                    var e = new EntityNotFoundException("Bill of id " + billId + " not found!");
                    logger.error(e.getMessage(), e);
                    return e;
                });
        repository
                .findAllById(products)
                .stream()
                .filter( product -> product.getShoppingList().getId().equals(bill.getShoppingList().getId()))
                .forEach(product -> {
                    product.setBill(bill);
                    product.setStatus(ProductStatus.PURCHASED);
                });
    }
}
