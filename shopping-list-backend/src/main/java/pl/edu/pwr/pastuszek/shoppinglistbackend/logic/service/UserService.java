package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

@Service
public class UserService extends SimpleCrudService<User> {
    public UserService(BaseRepository<User> repository) {
        super(repository, LoggerFactory.getLogger(UserService.class));
    }
}