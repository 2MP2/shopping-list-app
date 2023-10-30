package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.OrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserOrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.OrganizationSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.OrganizationDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.OrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.OrganizationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganizationStatus;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.UUID;

@Service
public class OrganizationService extends MappedCrudService<Organization, OrganizationRequestDTO, OrganizationResponseDTO> {
    private final UserOrganizationRepository userOrganizationRepository;
    public OrganizationService(OrganizationRepository repository,
                               OrganizationDTOMapper mapper,
                               OrganizationSpecifications organizationSpecifications,
                               UserOrganizationRepository userOrganizationRepository,
                               UserAuthentication userAuthentication
    ) {
        super(repository, LoggerFactory.getLogger(OrganizationService.class), mapper, organizationSpecifications, userAuthentication);
        this.userOrganizationRepository = userOrganizationRepository;
    }


    @Override
    public OrganizationResponseDTO add(OrganizationRequestDTO organizationRequestDTO) throws RuntimeException {
        OrganizationResponseDTO organizationResponseDTO = super.add(organizationRequestDTO);
        userOrganizationRepository.save(
                UserOrganization
                        .builder()
                        .status(UserOrganizationStatus.USER)
                        .user(userAuthentication.getAuthenticationInfo())
                        .organization(Organization.builder().id(organizationResponseDTO.getId()).build())
                        .build()
        );
        return organizationResponseDTO;
    }

    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;

        String userString = params.get("user");
        if(userString != null){
            UUID userId = UUID.fromString(userString);
            return userAuthentication.isCurrentUserHaveThisUUID(userId);
        }
        return true;
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserInOrganization(id);
    }

    @Override
    protected boolean isValidToAdd(OrganizationRequestDTO organizationRequestDTO) {
        return true;
    }

    @Override
    protected boolean isValidToUpdate(Organization entity, OrganizationRequestDTO organizationRequestDTO) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserOwnerInOrganization(entity.getId());
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserOwnerInOrganization(id);
    }
}
