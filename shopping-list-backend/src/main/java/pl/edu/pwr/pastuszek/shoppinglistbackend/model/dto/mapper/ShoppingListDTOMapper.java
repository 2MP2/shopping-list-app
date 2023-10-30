package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.OrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ShoppingListRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ShoppingListResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;


@Component
public class ShoppingListDTOMapper extends DTOMapper<ShoppingList, ShoppingListRequestDTO, ShoppingListResponseDTO>{
    private final OrganizationRepository organizationRepository;
    public ShoppingListDTOMapper(ModelMapper modelMapper, OrganizationRepository organizationRepository) {
        super(modelMapper);
        this.organizationRepository = organizationRepository;
    }

    @Override
    public ShoppingList convertDtoToEmptyEntity(ShoppingListRequestDTO dto) {
        return this.modelMapper.map(dto, ShoppingList.class);
    }

    @Override
    public ShoppingList convertDtoToFullEntity(ShoppingListRequestDTO dto) {
        ShoppingList shoppingList = convertDtoToEmptyEntity(dto);

        shoppingList.setOrganization(
                organizationRepository.findById(shoppingList.getOrganization().getId())
                        .orElseThrow(()-> new EntityNotFoundException(
                                "organization with id: " + shoppingList.getOrganization().getId() + " dose not exists"
                        )));
        return shoppingList;
    }

    @Override
    public ShoppingListResponseDTO convertEntityToDTO(ShoppingList entity) {
        return this.modelMapper.map(entity, ShoppingListResponseDTO.class);
    }
}
