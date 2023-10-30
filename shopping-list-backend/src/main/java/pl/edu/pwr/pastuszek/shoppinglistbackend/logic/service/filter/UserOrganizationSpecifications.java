package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.*;

import java.util.Map;
import java.util.UUID;

@Component
public class UserOrganizationSpecifications implements CreatSpecifications<UserOrganization> {
    @Override
    public Specification<UserOrganization> creat(Map<String, String> params) {
        Specification<UserOrganization> spec = Specification.where(null);
        if(params.get("status")!=null)
            spec = spec.and(UserOrganizationSpecifications.withUserOrganizationStatus(params.get("status")));
        if(params.get("user")!=null)
            spec = spec.and(UserOrganizationSpecifications.withUserId((params.get("user"))));
        if(params.get("organization")!=null)
            spec = spec.and(UserOrganizationSpecifications.withOrganizationId((params.get("organization"))));
        return spec;
    }

    public static Specification<UserOrganization> withUserOrganizationStatus(String status) {
        UserOrganizationStatus userOrganizationStatus = switch (status.toUpperCase()){
            case "OWNER" -> UserOrganizationStatus.OWNER;
            case "ADMIN" -> UserOrganizationStatus.ADMIN;
            case "USER" -> UserOrganizationStatus.USER;
            default -> null;
        };
        return (root, query, builder) -> builder.equal(root.get("status"), userOrganizationStatus);
    }

    public static Specification<UserOrganization> withUserId(String userId) {
        return (root, query, criteriaBuilder) -> {
            Join<UserOrganization, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("id"), UUID.fromString(userId));
        };
    }

    public static Specification<UserOrganization> withOrganizationId(String organizationId) {
        return (root, query, criteriaBuilder) -> {
            Join<UserOrganization, Organization> organizationJoin = root.join("organization");
            return criteriaBuilder.equal(organizationJoin.get("id"), UUID.fromString(organizationId));
        };
    }
}
