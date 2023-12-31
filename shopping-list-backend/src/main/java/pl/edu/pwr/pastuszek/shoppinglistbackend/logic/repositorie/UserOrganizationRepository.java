package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserOrganizationRepository extends BaseRepository<UserOrganization> {
    @Query("SELECT CASE WHEN COUNT(uo) > 0 THEN TRUE ELSE FALSE END " +
            "FROM UserOrganization uo " +
            "WHERE uo.user.id = :userId AND uo.organization.id = :orgId")
    boolean existsUserOrganizationByUserIdAndOrgId(
            @Param("userId") UUID userId,
            @Param("orgId") UUID orgId
    );

    Optional<UserOrganization> findByUserIdAndOrganizationId(UUID user_id, UUID organization_id);
}
