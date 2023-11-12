package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BillRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ProductRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.ProductSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.ProductDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ProductRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ProductResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductService extends MappedCrudService<Product, ProductRequestDTO, ProductResponseDTO> {

    private final BillRepository billRepository;
    private final ShoppingListRepository shoppingListRepository;
    public ProductService(ProductRepository repository,
                          ProductDTOMapper mapper,
                          BillRepository billRepository,
                          ProductSpecifications productSpecifications,
                          UserAuthentication userAuthentication,
                          ShoppingListRepository shoppingListRepository) {
        super(repository, LoggerFactory.getLogger(ProductService.class), mapper, productSpecifications, userAuthentication);
        this.billRepository = billRepository;
        this.shoppingListRepository = shoppingListRepository;
    }


    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;

        String billString = params.get("bill");
        String shoppingListString = params.get("shoppingList");

        if((billString == null || billString.isEmpty()) && shoppingListString == null) return false;

        if(shoppingListString != null) {
            UUID shoppingListId = UUID.fromString(shoppingListString);
            if(! userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId))) return  false;
        }

        if(billString != null && !billString.isEmpty()) {
            UUID billId = UUID.fromString(billString);
            return userAuthentication.isCurrentUserInOrganization(billRepository.findOrganizationIdById(billId));
        }

        return true;
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserInOrganization(((ProductRepository) repository).findOrganizationIdById(id));
    }

    @Override
    protected boolean isValidToAdd(ProductRequestDTO productRequestDTO) {
        if(userAuthentication.isAdmin()) return true;
        UUID shoppingListId = UUID.fromString(productRequestDTO.getShoppingListId());
        return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
    }

    @Override
    protected boolean isValidToUpdate(Product entity, ProductRequestDTO productRequestDTO) {
        if(userAuthentication.isAdmin()) return true;
        UUID shoppingListId = UUID.fromString(productRequestDTO.getShoppingListId());
        if(!shoppingListId.equals(entity.getShoppingList().getId())) return false;
        return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        return isValidToGetOne(id);
    }

    public void addBillAndPURCHASEDToProductsByIds(UUID billId, Set<UUID> products){
        if(! isValidToAddBillAndPURCHASEDToProductsByIds(billId)) throw new AccessDeniedException("Access denied!");
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
                    product.setPurchased(Boolean.TRUE);
                });
    }

    protected boolean isValidToAddBillAndPURCHASEDToProductsByIds(UUID billId){
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserInOrganization(billRepository.findOrganizationIdById(billId));
    }
}
