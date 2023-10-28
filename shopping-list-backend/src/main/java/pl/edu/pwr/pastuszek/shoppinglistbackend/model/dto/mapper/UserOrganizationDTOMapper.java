package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.OrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserOrganizationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserOrganizationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

@Component
public class UserOrganizationDTOMapper extends DTOMapper<UserOrganization, UserOrganizationRequestDTO, UserOrganizationResponseDTO> {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    public UserOrganizationDTOMapper(ModelMapper modelMapper, UserRepository userRepository, OrganizationRepository organizationRepository) {
        super(modelMapper);
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public UserOrganization convertDtoToEmptyEntity(UserOrganizationRequestDTO dto) {
        return this.modelMapper.map(dto, UserOrganization.class);
    }

    @Override
    public UserOrganization convertDtoToFullEntity(UserOrganizationRequestDTO dto) {
        UserOrganization userOrganization = convertDtoToEmptyEntity(dto);

        userOrganization.setUser(
                userRepository.findById(userOrganization.getUser().getId())
                        .orElseThrow(()-> new EntityNotFoundException(
                                "user with id: " + userOrganization.getUser().getId() + " dose not exists"
                        )));

        userOrganization.setOrganization(
                organizationRepository.findById(userOrganization.getOrganization().getId())
                        .orElseThrow(()-> new EntityNotFoundException(
                                "organization with id: " + userOrganization.getOrganization().getId() + " dose not exists"
                        )));
        return userOrganization;
    }

    @Override
    public UserOrganizationResponseDTO convertEntityToDTO(UserOrganization entity) {
        return this.modelMapper.map(entity, UserOrganizationResponseDTO.class);
    }
}
