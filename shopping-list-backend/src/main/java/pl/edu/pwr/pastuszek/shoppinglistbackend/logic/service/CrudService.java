package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * Base interface for a crud service.
 * @param <U> entity type used for creating a request
 * @param <D> returned entity type
 */
public interface CrudService<U, D> {
    Page<D> list(Map<String, String> params, Pageable pageable);

    default Page<D> list(Pageable pageable) {
        return list(Collections.emptyMap(), pageable);
    }

    D getOne(UUID id) throws RuntimeException;

    D add(U u) throws RuntimeException;

    D update(UUID id, U u) throws RuntimeException;

    void delete(UUID id);

}
