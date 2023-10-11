package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.InvitationDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.InvitationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.InvitationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;

@Service
public class InvitationService extends MappedCrudService<Invitation, InvitationRequestDTO, InvitationResponseDTO> {
    public InvitationService(BaseRepository<Invitation> repository, InvitationDTOMapper mapper) {
        super(repository, LoggerFactory.getLogger(InvitationService.class), mapper);
    }
}
