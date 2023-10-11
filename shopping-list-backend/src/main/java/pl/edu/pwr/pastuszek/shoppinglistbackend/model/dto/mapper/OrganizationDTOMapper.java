package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.OrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.OrganizationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;

@Component
public class OrganizationDTOMapper extends DTOMapper<Organization, OrganizationRequestDTO, OrganizationResponseDTO>{
    public OrganizationDTOMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public Organization convertDtoToEmptyEntity(OrganizationRequestDTO dto) {
        return this.modelMapper.map(dto, Organization.class);
    }

    @Override
    public Organization convertDtoToFullEntity(OrganizationRequestDTO dto) {
        return convertDtoToEmptyEntity(dto);
    }

    @Override
    public OrganizationResponseDTO convertEntityToDTO(Organization entity) {
        return this.modelMapper.map(entity, OrganizationResponseDTO.class);
    }
}
