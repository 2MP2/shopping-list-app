package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;

@Service
public class InvitationService extends SimpleCrudService<Invitation> {
    public InvitationService(BaseRepository<Invitation> repository) {
        super(repository, LoggerFactory.getLogger(InvitationService.class));
    }
}
