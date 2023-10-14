package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.UserDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserFullResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

import java.util.UUID;

@Service
public class UserService extends MappedCrudService<User, UserRequestDTO, UserResponseDTO> {
    public UserService(UserRepository repository, UserDTOMapper mapper) {
        super(repository, LoggerFactory.getLogger(UserService.class), mapper);
    }

    public UserFullResponseDTO getFullUserDto(UUID id){
        User user = repository.findById(id)
                .orElseThrow(()->{
                    var e = new EntityNotFoundException("User of id " + id + " not found!");
                    logger.error(e.getMessage(), e);
                    return e;
                });

        return ((UserDTOMapper) mapper).convertToFullUserDto(user);
    }
}
