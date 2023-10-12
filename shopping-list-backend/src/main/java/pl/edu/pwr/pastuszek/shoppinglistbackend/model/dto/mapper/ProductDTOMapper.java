package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ProductRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ProductResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;

@Component
public class ProductDTOMapper extends DTOMapper<Product, ProductRequestDTO, ProductResponseDTO> {
    private final ShoppingListRepository shoppingListRepository;
    public ProductDTOMapper(ModelMapper modelMapper, ShoppingListRepository shoppingListRepository) {
        super(modelMapper);
        this.shoppingListRepository = shoppingListRepository;
    }

    @Override
    public Product convertDtoToEmptyEntity(ProductRequestDTO dto) {
        return this.modelMapper.map(dto, Product.class);
    }

    @Override
    public Product convertDtoToFullEntity(ProductRequestDTO dto) {
        Product product = convertDtoToEmptyEntity(dto);

        product.setShoppingList(
                shoppingListRepository.findById(product.getShoppingList().getId())
                        .orElseThrow(()-> new IllegalStateException(
                                "shopping list with id: " + product.getShoppingList().getId() + " dose not exists"
                        )));

        return product;
    }

    @Override
    public ProductResponseDTO convertEntityToDTO(Product entity) {
        return this.modelMapper.map(entity, ProductResponseDTO.class);
    }
}
