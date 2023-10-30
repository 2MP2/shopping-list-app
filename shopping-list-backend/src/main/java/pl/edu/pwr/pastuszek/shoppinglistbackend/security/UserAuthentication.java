package pl.edu.pwr.pastuszek.shoppinglistbackend.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserOrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganizationStatus;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAuthentication {
    private final UserOrganizationRepository userOrganizationRepository;
    private final Logger logger = LoggerFactory.getLogger(UserAuthentication.class);

    public User getAuthenticationInfo() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isAdmin() {
        return getAuthenticationInfo().getRole().name().equals("ADMIN");
    }

    public boolean isCurrentUserHaveThisUUID(UUID id) {
        return getAuthenticationInfo().getId().equals(id);
    }

    public boolean isCurrentUserInOrganization(UUID organizationUUID) {
        final UUID currentUserUUID = getAuthenticationInfo().getId();
        return userOrganizationRepository.existsUserOrganizationByUserIdAndOrgId(
                currentUserUUID,
                organizationUUID
        );
    }

    public boolean isMinAdminInOrganization(UUID organizationUUID){
        UserOrganization userOrganization = userOrganizationRepository
                .findUserOrganizationByUserIdAndOrgId(getAuthenticationInfo().getId(), organizationUUID)
                .orElseThrow(()->{
                    var e = new EntityNotFoundException("User with id " + getAuthenticationInfo().getId() + " doesn't belongs to organization with id " + organizationUUID);
                    logger.error(e.getMessage(), e);
                    return e;
                });

        return userOrganization.getStatus().equals(UserOrganizationStatus.ADMIN)
                || userOrganization.getStatus().equals(UserOrganizationStatus.OWNER);
    }

    public boolean isCurrentUserOwnerInOrganization(UUID organizationUUID){
        UserOrganization userOrganization = userOrganizationRepository
                .findUserOrganizationByUserIdAndOrgId(getAuthenticationInfo().getId(), organizationUUID)
                .orElseThrow(()->{
                    var e = new EntityNotFoundException("User with id " + getAuthenticationInfo().getId() + " doesn't belongs to organization with id " + organizationUUID);
                    logger.error(e.getMessage(), e);
                    return e;
                });
        return userOrganization.getStatus().equals(UserOrganizationStatus.OWNER);
    }
}
