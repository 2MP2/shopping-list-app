package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

/**
 *
 * @param <T> Entity
 * @param <U> DTO
 */
public interface MapperResponseDTO<T,U> {
    U convertEntityToDTO(T entity);
}
