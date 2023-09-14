package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Base interface for a crud service.
 * @param <U> entity type used for creating a request
 * @param <D> returned entity type
 */
public interface CrudService<U, D> {
    List<D> list(Map<String, String> params);

    default List<D> list() {
        return list(Collections.emptyMap());
    }

    D getOne(UUID id) throws RuntimeException;

    D add(U u) throws RuntimeException;

    D update(UUID id, U u) throws RuntimeException;

    void delete(UUID id);
}
