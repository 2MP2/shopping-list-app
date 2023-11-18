package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.BillRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.BillResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;

@Component
public class BillDTOMapper extends DTOMapper<Bill, BillRequestDTO, BillResponseDTO> {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;
    public BillDTOMapper(ModelMapper modelMapper, ShoppingListRepository shoppingListRepository, UserRepository userRepository) {
        super(modelMapper);
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Bill convertDtoToEmptyEntity(BillRequestDTO dto) {
        return this.modelMapper.map(dto, Bill.class);
    }

    @Override
    public Bill convertDtoToFullEntity(BillRequestDTO dto) {
        Bill bill = convertDtoToEmptyEntity(dto);
        bill.setShoppingList(
                shoppingListRepository.findById(bill.getShoppingList().getId())
                        .orElseThrow(()->new EntityNotFoundException(
                                "shopping list with id: " + bill.getShoppingList().getId() + " dose not exists"
                        )));
        bill.setUser(
                userRepository.findById(bill.getUser().getId())
                    .orElseThrow(()->new EntityNotFoundException(
                            "user with id: " + bill.getUser().getId() + " dose not exists"
                    )));
        return bill;
    }

    @Override
    public BillResponseDTO convertEntityToDTO(Bill entity) {
        return this.modelMapper.map(entity, BillResponseDTO.class);
    }
}
