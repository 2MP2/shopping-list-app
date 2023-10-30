package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

import java.util.Map;
import java.util.UUID;

@Component
public class InvitationSpecifications implements CreatSpecifications<Invitation> {
    @Override
    public Specification<Invitation> creat(Map<String, String> params) {
        Specification<Invitation> spec = Specification.where(null);
        if(params.get("user")!=null)
            spec = spec.and(InvitationSpecifications.withUserId((params.get("user"))));
        if(params.get("organization")!=null)
            spec = spec.and(InvitationSpecifications.withOrganizationId((params.get("organization"))));
        return spec;
    }

    public static Specification<Invitation> withUserId(String userId) {
        return (root, query, criteriaBuilder) -> {
            Join<Invitation, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("id"), UUID.fromString(userId));
        };
    }

    public static Specification<Invitation> withOrganizationId(String organizationId) {
        return (root, query, criteriaBuilder) -> {
            Join<Invitation, Organization> organizationJoin = root.join("organization");
            return criteriaBuilder.equal(organizationJoin.get("id"), UUID.fromString(organizationId));
        };
    }


}
