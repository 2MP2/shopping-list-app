package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;

@Service
public class OrganizationService extends SimpleCrudService<Organization> {
    public OrganizationService(BaseRepository<Organization> repository) {
        super(repository, LoggerFactory.getLogger(OrganizationService.class));
    }
}
