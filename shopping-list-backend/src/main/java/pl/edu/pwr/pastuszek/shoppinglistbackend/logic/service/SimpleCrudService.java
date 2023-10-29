package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.CreatSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Entity;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.UUID;

/**
 * This is an abstract class for simple CRUD services.
 * This class doesn't take care of mapping entities - it takes and returns the exact same object that is defined in
 * {@link pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity}  package.
 * @param <T> the domain type the service manage. Extends {@link Entity} class.
 */
public abstract class SimpleCrudService<T extends Entity>
        extends AbstractCrudService<T, T, T>
{

    protected SimpleCrudService(BaseRepository<T> repository,
                                Logger logger,
                                CreatSpecifications<T> creatSpecifications,
                                UserAuthentication userAuthentication
    ) {
        super(repository, logger, creatSpecifications, userAuthentication);
    }

    @Override
    public Page<T> list(Map<String, String> params, Pageable pageable) {
        return repository.findAll(creatSpecifications.creat(params), pageable);
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
        t.setId(null);
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
