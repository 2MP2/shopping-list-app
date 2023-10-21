package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserOrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.UserOrganizationSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.UserOrganizationDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserOrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserOrganizationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

@Service
public class UserOrganizationService extends MappedCrudService<UserOrganization, UserOrganizationRequestDTO, UserOrganizationResponseDTO> {
    public UserOrganizationService(UserOrganizationRepository repository, UserOrganizationDTOMapper mapper, UserOrganizationSpecifications userOrganizationSpecifications) {
        super(repository, LoggerFactory.getLogger(UserOrganizationService.class), mapper, userOrganizationSpecifications);
    }
}
