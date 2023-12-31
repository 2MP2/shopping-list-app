package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.CreatSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Entity;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

@Transactional
@RequiredArgsConstructor
public abstract class AbstractCrudService<T extends Entity, U, D> implements CrudService<U, D> {

    protected final BaseRepository<T> repository;
    protected final Logger logger;
    protected final CreatSpecifications<T> creatSpecifications;
    protected final UserAuthentication userAuthentication;

}
