package pl.edu.pwr.pastuszek.shoppinglistbackend.security;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAuthentication {
    UserRepository userRepository;

    public User getAuthenticationInfo() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isAdmin() {
        return getAuthenticationInfo().getRole().name().equals("ADMIN");
    }

    public boolean isCurrentUserHaveUUID(UUID id) {
        return getAuthenticationInfo().getId().equals(id);
    }

    public boolean isCurrentUserInOrganization(Supplier<UUID> getOrganizationUUID) {
        return userRepository.findUserOrganizationsByUserId(getAuthenticationInfo().getId())
                .stream()
                .anyMatch(
                        userOrganization -> userOrganization.getUser().getId().equals(getAuthenticationInfo().getId()) &&
                                userOrganization.getOrganization().getId().equals(getOrganizationUUID.get())
                );
    }
}
