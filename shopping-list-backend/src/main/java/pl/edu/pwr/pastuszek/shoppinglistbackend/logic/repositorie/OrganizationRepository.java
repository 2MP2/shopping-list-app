package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;

@Repository
public interface OrganizationRepository extends SoftDeleteRepository<Organization>{
}
