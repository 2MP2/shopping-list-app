package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.OrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.OrganizationDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.OrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.OrganizationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;

@Service
public class OrganizationService extends MappedCrudService<Organization, OrganizationRequestDTO, OrganizationResponseDTO> {
    public OrganizationService(OrganizationRepository repository, OrganizationDTOMapper mapper) {
        super(repository, LoggerFactory.getLogger(OrganizationService.class), mapper);
    }
}
