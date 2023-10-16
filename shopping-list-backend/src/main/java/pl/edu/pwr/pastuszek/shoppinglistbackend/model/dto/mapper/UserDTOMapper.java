package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.UserRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

@Component
public class UserDTOMapper extends DTOMapper<User, UserRequestDTO, UserResponseDTO> {
    public UserDTOMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public User convertDtoToEmptyEntity(UserRequestDTO dto) {
        return this.modelMapper.map(dto, User.class);
    }

    @Override
    public User convertDtoToFullEntity(UserRequestDTO dto) {
        return convertDtoToEmptyEntity(dto);
    }

    @Override
    public UserResponseDTO convertEntityToDTO(User entity) {
        return this.modelMapper.map(entity, UserResponseDTO.class);
    }
}
