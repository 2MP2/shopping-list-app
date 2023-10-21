package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.ShoppingListSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.ShoppingListDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ShoppingListRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ShoppingListResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;

@Service
public class ShoppingListService extends MappedCrudService<ShoppingList, ShoppingListRequestDTO, ShoppingListResponseDTO> {
    public ShoppingListService(ShoppingListRepository repository, ShoppingListDTOMapper mapper, ShoppingListSpecifications shoppingListSpecifications) {
        super(repository, LoggerFactory.getLogger(ShoppingListService.class), mapper, shoppingListSpecifications);
    }
}
