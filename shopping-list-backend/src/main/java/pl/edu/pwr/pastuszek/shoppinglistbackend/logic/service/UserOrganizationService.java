package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

public class UserOrganizationService extends SimpleCrudService<UserOrganization> {
    public UserOrganizationService(BaseRepository<UserOrganization> repository) {
        super(repository, LoggerFactory.getLogger(UserOrganizationService.class));
    }
}
