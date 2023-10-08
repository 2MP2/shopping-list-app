package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;

@Service
public class ShoppingListService extends SimpleCrudService<ShoppingList> {
    public ShoppingListService(ShoppingListRepository repository) {
        super(repository, LoggerFactory.getLogger(ShoppingListService.class));
    }
}
