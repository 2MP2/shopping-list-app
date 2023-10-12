package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

@Repository
public interface UserOrganizationRepository extends SoftDeleteRepository<UserOrganization> {
}
