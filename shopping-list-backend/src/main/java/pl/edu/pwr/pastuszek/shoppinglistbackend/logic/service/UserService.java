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
        return false;
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        return false;
    }

    @Override
    protected boolean isValidToAdd(UserRequestDTO userRequestDTO) {
        return false;
    }

    @Override
    protected boolean isValidToUpdate(UUID id, UserRequestDTO userRequestDTO) {
        return false;
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        return false;
    }
}
