package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.UserSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.UserDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.UUID;

@Service
public class UserService extends MappedCrudService<User, UserRequestDTO, UserResponseDTO> {
    public UserService(UserRepository repository,
                       UserDTOMapper mapper,
                       UserSpecifications userSpecifications,
                       UserAuthentication userAuthentication
    ) {
        super(repository, LoggerFactory.getLogger(UserService.class), mapper, userSpecifications, userAuthentication);
    }

    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;
        String organizationString = params.get("organization");
        if(organizationString == null) return false;
        UUID organizationId = UUID.fromString(organizationString);
        return userAuthentication.isCurrentUserInOrganization(organizationId);

    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserHaveThisUUID(id);
    }

    @Override
    protected boolean isValidToAdd(UserRequestDTO userRequestDTO) {
        return userAuthentication.isAdmin();
    }

    @Override
    protected boolean isValidToUpdate(User entity, UserRequestDTO userRequestDTO) {
        return userAuthentication.isAdmin();// update is not available for basic user (temporary)
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        return isValidToGetOne(id);
    }
}
