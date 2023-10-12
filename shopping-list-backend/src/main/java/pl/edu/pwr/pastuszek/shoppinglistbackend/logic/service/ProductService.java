package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ProductRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.ProductDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ProductRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ProductResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;

@Service
public class ProductService extends MappedCrudService<Product, ProductRequestDTO, ProductResponseDTO> {
    public ProductService(ProductRepository repository, ProductDTOMapper mapper) {
        super(repository, LoggerFactory.getLogger(ProductService.class), mapper);
    }
}
