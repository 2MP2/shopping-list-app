package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BillRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.ShoppingListSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.ShoppingListDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ShoppingListRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ShoppingListResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.UUID;

@Service
public class ShoppingListService extends MappedCrudService<ShoppingList, ShoppingListRequestDTO, ShoppingListResponseDTO> {
    private final BillRepository billRepository;
    public ShoppingListService(ShoppingListRepository repository,
                               ShoppingListDTOMapper mapper,
                               ShoppingListSpecifications shoppingListSpecifications,
                               UserAuthentication userAuthentication,
                               BillRepository billRepository) {
        super(repository, LoggerFactory.getLogger(ShoppingListService.class), mapper, shoppingListSpecifications, userAuthentication);
        this.billRepository = billRepository;
    }

    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;

        String organizationString = params.get("organization");
        String billString = params.get("bill");

        if(organizationString == null && billString == null) return false;

        if(billString != null) {
            UUID billId = UUID.fromString(billString);
            if(! userAuthentication.isCurrentUserInOrganization(billRepository.findOrganizationIdById(billId))) return  false;
        }

        if(organizationString != null) {
            UUID organizationId = UUID.fromString(organizationString);
            return userAuthentication.isCurrentUserInOrganization(organizationId);
        }

        return true;
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserInOrganization(((ShoppingListRepository) repository).findOrganizationIdById(id));
    }

    @Override
    protected boolean isValidToAdd(ShoppingListRequestDTO shoppingListRequestDTO) {
        if(userAuthentication.isAdmin()) return true;
        UUID organizationId = UUID.fromString(shoppingListRequestDTO.getOrganizationId());
        return userAuthentication.isCurrentUserInOrganization(organizationId);
    }

    @Override
    protected boolean isValidToUpdate(ShoppingList entity, ShoppingListRequestDTO shoppingListRequestDTO) {
        if(userAuthentication.isAdmin()) return true;

        UUID organizationId = UUID.fromString(shoppingListRequestDTO.getOrganizationId());

        if(organizationId != entity.getOrganization().getId()) return false;

        if(!userAuthentication.isCurrentUserInOrganization(((ShoppingListRepository) repository).findOrganizationIdById(entity.getId()))) return false;
        return userAuthentication.isCurrentUserInOrganization(organizationId);
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        return isValidToGetOne(id);
    }
}
