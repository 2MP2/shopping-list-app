package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @param <T> Entity
 * @param <U> request DTO
 * @param <D> response DTO
 */

@RequiredArgsConstructor
public abstract class DTOMapper<T,U,D> implements MapperRequestDTO<T,U>, MapperResponseDTO<T,D> {
    final ModelMapper modelMapper;
}
