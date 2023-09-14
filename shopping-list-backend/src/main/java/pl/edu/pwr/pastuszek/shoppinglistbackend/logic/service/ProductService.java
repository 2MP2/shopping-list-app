package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;

@Service
public class ProductService extends SimpleCrudService<Product>{
    public ProductService(BaseRepository<Product> repository) {
        super(repository, LoggerFactory.getLogger(ProductService.class));
    }
}
