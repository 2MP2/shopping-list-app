package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.exception.custom.InvitationException;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.InvitationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserOrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.InvitationSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.InvitationDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.InvitationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.InvitationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganizationStatus;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.Map;
import java.util.UUID;

@Service
public class InvitationService extends MappedCrudService<Invitation, InvitationRequestDTO, InvitationResponseDTO> {
    private final UserOrganizationRepository userOrganizationRepository;
    public InvitationService(InvitationRepository repository,
                             InvitationDTOMapper mapper,
                             UserOrganizationRepository userOrganizationRepository,
                             InvitationSpecifications invitationSpecifications,
                             UserAuthentication userAuthentication
    ) {
        super(repository, LoggerFactory.getLogger(InvitationService.class), mapper, invitationSpecifications, userAuthentication);
        this.userOrganizationRepository = userOrganizationRepository;
    }

    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;

        String userString = params.get("user");
        String organizationString = params.get("organization");

        if(userString == null && organizationString == null) return false;

        if(organizationString != null){
            UUID organizationId = UUID.fromString(organizationString);
            return userAuthentication.isCurrentUserInOrganization(organizationId);
        }

        UUID userId = UUID.fromString(userString);
        return userAuthentication.isCurrentUserHaveThisUUID(userId);
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserInOrganization(((InvitationRepository) repository).findOrganizationIdById(id));
    }

    @Override
    protected boolean isValidToAdd(InvitationRequestDTO invitationRequestDTO) {
        UUID organizationId = UUID.fromString(invitationRequestDTO.getOrganizationId());
        if(invitationRequestDTO.getUserEmail()!=null
                && (((InvitationRepository)repository)
                .existsInvitationByUserEmailAndOrgId(invitationRequestDTO.getUserEmail(), organizationId))){
            throw new InvitationException("Invitation already exist");
        }
        if(invitationRequestDTO.getUserNumber()!=null
                && (((InvitationRepository)repository)
                .existsInvitationByUserNumberAndOrgId(invitationRequestDTO.getUserNumber(), organizationId))){
            throw new InvitationException("Invitation already exist");
        }

        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isMinAdminInOrganization(organizationId);
    }

    @Override
    protected boolean isValidToUpdate(Invitation entity, InvitationRequestDTO invitationRequestDTO) {
        return userAuthentication.isAdmin();
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        if(userAuthentication.isMinAdminInOrganization(((InvitationRepository) repository).findOrganizationIdById(id))) return true;
        Invitation invitation = repository.findById(id).orElse(null);
        if(invitation == null) return false;
        return userAuthentication.isCurrentUserHaveThisUUID(invitation.getUser().getId());
    }

    public void acceptInvitation(UUID id, boolean isAccepted){

        Invitation invitation = repository.findById(id)
                .orElseThrow(()->{
                    var e = new EntityNotFoundException("Invitation of id " + id + " not found!");
                    logger.error(e.getMessage(), e);
                    return e;
                });
        if(!isValidToAcceptInvitation(invitation)) throw new AccessDeniedException("Access denied!");

        if(isAccepted){
            userOrganizationRepository.save(
                    UserOrganization
                            .builder()
                            .status(UserOrganizationStatus.USER)
                            .user(invitation.getUser())
                            .organization(invitation.getOrganization())
                            .build()
            );
        }

        delete(invitation.getId());
    }

    protected boolean isValidToAcceptInvitation(Invitation invitation){
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserHaveThisUUID(invitation.getUser().getId());
    }
}
