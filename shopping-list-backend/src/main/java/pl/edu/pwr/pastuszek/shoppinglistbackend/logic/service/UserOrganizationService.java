package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserOrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.UserOrganizationSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.UserOrganizationDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserOrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserOrganizationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.UUID;

@Service
public class UserOrganizationService extends MappedCrudService<UserOrganization, UserOrganizationRequestDTO, UserOrganizationResponseDTO> {
    public UserOrganizationService(UserOrganizationRepository repository,
                                   UserOrganizationDTOMapper mapper,
                                   UserOrganizationSpecifications userOrganizationSpecifications,
                                   UserAuthentication userAuthentication
    ) {
        super(repository, LoggerFactory.getLogger(UserOrganizationService.class), mapper, userOrganizationSpecifications, userAuthentication);
    }

    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;
        String userString = params.get("user");
        String organizationString = params.get("organization");

        if(userString == null && organizationString == null) return false;

        if(userString != null){
            UUID userId = UUID.fromString(userString);
            if(! userAuthentication.isCurrentUserHaveThisUUID(userId)) return false;
        }

        if(organizationString != null) {
            UUID organizationId = UUID.fromString(organizationString);
            return userAuthentication.isCurrentUserInOrganization(organizationId);
        }

        return true;
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        return userAuthentication.isAdmin();
    }

    @Override
    protected boolean isValidToAdd(UserOrganizationRequestDTO userOrganizationRequestDTO) {
        return userAuthentication.isAdmin();
    }

    @Override
    protected boolean isValidToUpdate(UserOrganization entity, UserOrganizationRequestDTO userOrganizationRequestDTO) {
        if(userAuthentication.isAdmin()) return true;

        UUID userId = UUID.fromString(userOrganizationRequestDTO.getUserId());
        if(!userId.equals(entity.getUser().getId())) return false;
        UUID organizationId = UUID.fromString(userOrganizationRequestDTO.getOrganizationId());
        if(!organizationId.equals(entity.getOrganization().getId())) return false;

        return userAuthentication.isCurrentUserOwnerInOrganization(organizationId);
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        UserOrganization userOrganization = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(
                "UserOrganization with id: " + id + " dose not exists"
        ));

        System.out.println(userOrganization);

        if(userAuthentication.isCurrentUserOwnerInOrganization(userOrganization.getOrganization().getId())) return true;
        return userAuthentication.isCurrentUserHaveThisUUID(userOrganization.getUser().getId());
    }
}
