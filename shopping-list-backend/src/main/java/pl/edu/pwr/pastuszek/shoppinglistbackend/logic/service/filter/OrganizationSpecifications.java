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
public class OrganizationSpecifications implements CreatSpecifications<Organization> {
    @Override
    public Specification<Organization> creat(Map<String, String> params) {
        Specification<Organization> spec = Specification.where(null);
        if(params.get("user")!=null)
            spec = spec.and(OrganizationSpecifications.withUserId((params.get("user"))));
        return spec;
    }

    public static Specification<Organization> withUserId(String userId) {
        return (root, query, builder) -> {
            Join<Organization, UserOrganization> userOrganizationJoin = root.join("userOrganizations");
            Join<UserOrganization, User> userJoin = userOrganizationJoin.join("user");
            return builder.equal(userJoin.get("id"), UUID.fromString(userId));
        };
    }

}
