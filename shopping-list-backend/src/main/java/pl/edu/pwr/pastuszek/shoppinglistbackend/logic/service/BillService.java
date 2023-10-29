package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BillRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.BillSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.BillDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.BillRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.BillResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.UUID;

@Service
public class BillService extends MappedCrudService<Bill, BillRequestDTO, BillResponseDTO> {
    private final ShoppingListRepository shoppingListRepository;

    public BillService(BillRepository repository,
                       BillDTOMapper mapper,
                       BillSpecifications billSpecifications,
                       UserAuthentication userAuthentication,
                       ShoppingListRepository shoppingListRepository
    ) {
        super(repository, LoggerFactory.getLogger(BillService.class), mapper, billSpecifications , userAuthentication);
        this.shoppingListRepository = shoppingListRepository;
    }

    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;

        String userString = params.get("user");
        String shoppingListString = params.get("shoppingList");

        if(userString == null && shoppingListString == null) return false;

        if(userString != null){
            UUID userId = UUID.fromString(userString);
            if(! userAuthentication.isCurrentUserHaveThisUUID(userId)) return false;
        }

        if(shoppingListString != null) {
            UUID shoppingListId = UUID.fromString(shoppingListString);
            return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
        }

        return true;
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserInOrganization(((BillRepository) repository).findOrganizationIdById(id));
    }

    @Override
    protected boolean isValidToAdd(BillRequestDTO billRequestDTO) {
        UUID userId = UUID.fromString(billRequestDTO.getUserId());
        UUID shoppingListId = UUID.fromString(billRequestDTO.getShoppingListId());

        if(! userAuthentication.isCurrentUserHaveThisUUID(userId)) return false;
        return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
    }

    @Override
    protected boolean isValidToUpdate(Bill entity, BillRequestDTO billRequestDTO) {
        if(userAuthentication.isAdmin()) return true;

        UUID userId = UUID.fromString(billRequestDTO.getUserId());
        UUID shoppingListId = UUID.fromString(billRequestDTO.getShoppingListId());

        if(userId != entity.getUser().getId()) return false;
        if(shoppingListId != entity.getShoppingList().getId()) return false;

        if(! userAuthentication.isCurrentUserInOrganization(((BillRepository)repository).findOrganizationIdById(entity.getId()))) return false;
        if(! userAuthentication.isCurrentUserHaveThisUUID(userId)) return false;
        return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        return isValidToGetOne(id);
    }

}
