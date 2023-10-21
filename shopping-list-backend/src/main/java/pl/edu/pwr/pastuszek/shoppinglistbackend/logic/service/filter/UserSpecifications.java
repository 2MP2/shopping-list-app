package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

import java.util.Map;
import java.util.UUID;

@Component
public class UserSpecifications implements CreatSpecifications<User>{
    @Override
    public Specification<User> creat(Map<String, String> params) {
        Specification<User> spec = Specification.where(null);
        if(params.get("organization")!=null)
            spec = spec.and(UserSpecifications.withOrganizationId((params.get("organization"))));
        return spec;
    }

    public static Specification<User> withOrganizationId(String organizationId) {
        return (root, query, builder) -> {
            Join<User, UserOrganization> userOrganizationJoin = root.join("userOrganizations");
            Join<UserOrganization, Organization> userJoin = userOrganizationJoin.join("organization");
            return builder.equal(userJoin.get("id"), UUID.fromString(organizationId));
        };
    }
}
