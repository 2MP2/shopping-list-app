package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.CreatSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.DTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Entity;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is an abstract class for CRUD services which require DTO mapping.
 * It uses {@link DTOMapper} to map requests and responses from given domain entity.
 *
 * @param <T> the domain type the service manage. Implements {@link pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Entity} interface.
 * @param <U> the request type which is mapped to the domain type.
 * @param <D> the response type which is created from the domain type.
 */
public abstract class MappedCrudService<T extends Entity, U, D>
        extends AbstractCrudService<T, U, D>
{

    protected final DTOMapper<T, U, D> mapper;

    protected MappedCrudService(BaseRepository<T> repository,
                                Logger logger,
                                DTOMapper<T, U, D> mapper,
                                CreatSpecifications<T> creatSpecifications,
                                UserAuthentication userAuthentication
    ) {
        super(repository, logger, creatSpecifications, userAuthentication);
        this.mapper = mapper;
    }

    @Override
    public Page<D> list(Map<String, String> params, Pageable pageable) throws RuntimeException {
        if(!isValidToList(params, pageable)) throw new AccessDeniedException("Access denied!");
        Page<T> entityPage = repository.findAll(creatSpecifications.creat(params), pageable);

        List<D> dtoList = entityPage.getContent().stream()
                .map(mapper::convertEntityToDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    @Override
    public D getOne(UUID id) throws RuntimeException {
        if(!isValidToGetOne(id)) throw new AccessDeniedException("Access denied!");
        var entity = repository
                .findById(id)
                .orElseThrow(() -> {
                    var e = new EntityNotFoundException("Entity of id " + id + " not found!");
                    logger.error(e.getMessage(), e);
                    return e;
                });

        return mapper.convertEntityToDTO(entity);
    }

    @Override
    public D add(U u) throws RuntimeException {
        if(!isValidToAdd(u)) throw new AccessDeniedException("Access denied!");
        var entity = mapper.convertDtoToFullEntity(u);
        entity.setId(null);
        return mapper.convertEntityToDTO(repository.save(entity));
    }

    @Override
    public D update(UUID id, U u) throws RuntimeException {
        if(!isValidToUpdate(id, u)) throw new AccessDeniedException("Access denied!");
        if (!repository.existsById(id)) {
            var e = new EntityNotFoundException("Entity of id " + id + " doesn't exist!");
            logger.error(e.getMessage(), e);
            throw e;
        }

        var entity = mapper.convertDtoToFullEntity(u);
        entity.setId(id);
        return mapper.convertEntityToDTO(repository.save(entity));
    }

    @Override
    public void delete(UUID id) throws RuntimeException{
        if(!isValidToDelete(id)) throw new AccessDeniedException("Access denied!");
        repository.deleteById(id);
    }

    protected abstract boolean isValidToList(Map<String, String> params, Pageable pageable);
    protected abstract boolean isValidToGetOne(UUID id);
    protected abstract boolean isValidToAdd(U u);
    protected abstract boolean isValidToUpdate(UUID id, U u);
    protected abstract boolean isValidToDelete(UUID id);
}
