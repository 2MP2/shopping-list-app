package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

public class UserService extends SimpleCrudService<User> {
    public UserService(BaseRepository<User> repository) {
        super(repository, LoggerFactory.getLogger(UserService.class));
    }
}
