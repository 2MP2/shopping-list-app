package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.InvitationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserOrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.InvitationDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.InvitationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.InvitationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganizationStatus;

import java.util.UUID;

@Service
public class InvitationService extends MappedCrudService<Invitation, InvitationRequestDTO, InvitationResponseDTO> {
    private final UserOrganizationRepository userOrganizationRepository;
    public InvitationService(InvitationRepository repository, InvitationDTOMapper mapper, UserOrganizationRepository userOrganizationRepository) {
        super(repository, LoggerFactory.getLogger(InvitationService.class), mapper);
        this.userOrganizationRepository = userOrganizationRepository;
    }

    public void acceptToTrueAndAddUserOrganization(UUID id){
        Invitation invitation = repository.findById(id)
                .orElseThrow(()->{
                    var e = new EntityNotFoundException("Invitation of id " + id + " not found!");
                    logger.error(e.getMessage(), e);
                    return e;
                });
        System.out.println(invitation);
        System.out.println(invitation.getUser());
        System.out.println(invitation.getOrganization());
        userOrganizationRepository.save(
                UserOrganization
                        .builder()
                        .status(UserOrganizationStatus.USER)
                        .user(invitation.getUser())
                        .organization(invitation.getOrganization())
                        .build()
        );

    }
}
