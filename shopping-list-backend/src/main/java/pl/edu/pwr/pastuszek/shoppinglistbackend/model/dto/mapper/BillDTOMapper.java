package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.BillRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.BillResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;

@Component
public class BillDTOMapper extends DTOMapper<Bill, BillRequestDTO, BillResponseDTO> {

    private final ShoppingListRepository shoppingListRepository;
    public BillDTOMapper(ModelMapper modelMapper, ShoppingListRepository shoppingListRepository) {
        super(modelMapper);
        this.shoppingListRepository = shoppingListRepository;
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
                        .orElseThrow(()->new IllegalStateException(
                                "shopping list with id: " + bill.getShoppingList().getId() + " dose not exists"
                        )));
        return bill;
    }

    @Override
    public BillResponseDTO convertEntityToDTO(Bill entity) {
        return this.modelMapper.map(entity, BillResponseDTO.class);
    }
}
