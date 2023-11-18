package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;

import java.util.UUID;

@Repository
public interface InvitationRepository extends BaseRepository<Invitation> {
    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Invitation i " +
            "WHERE i.user.email = :userEmail AND i.organization.id = :orgId")
    boolean existsInvitationByUserEmailAndOrgId(
            @Param("userEmail") String userEmail,
            @Param("orgId") UUID orgId
    );

    @Query("SELECT i.organization.id FROM Invitation i WHERE i.id = :invitationId")
    UUID findOrganizationIdById(@Param("invitationId") UUID invitationId);

    long countByUser_Id(UUID userId);
}
