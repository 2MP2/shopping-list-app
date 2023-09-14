package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.DatabaseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is an abstract class for simple CRUD services.
 * This class doesn't take care of mapping entities - it takes and returns the exact same object that is defined in
 * {@link pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity}  package.
 * @param <T> the domain type the service manage. Extends {@link pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.DatabaseEntity} class.
 */
public abstract class SimpleCrudService<T extends DatabaseEntity>
        extends AbstractCrudService<T, T, T>
{

    public SimpleCrudService(BaseRepository<T> repository, Logger logger) {
        super(repository, logger);
    }

    @Override
    public List<T> list(Map<String, String> params) {
        return repository.findAll();
    }

    @Override
    public T getOne(UUID id) throws RuntimeException {
        return repository
                .findById(id)
                .orElseThrow(() -> {
                    var e = new EntityNotFoundException("Entity of id " + id + " not found!");
                    logger.error(e.getMessage(), e);
                    return e;
                });
    }

    @Override
    public T add(T t) throws RuntimeException {
//        t.setId(0);
        return repository.save(t);
    }

    @Override
    public T update(UUID id, T t) throws RuntimeException {
        if (!repository.existsById(id)) {
            var e = new EntityNotFoundException("Entity " + t.getClass().getSimpleName() + " of id " + id + " doesn't exist!");
            logger.error(e.getMessage(), e);
            throw e;
        }

        t.setId(id);
        return repository.save(t);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
