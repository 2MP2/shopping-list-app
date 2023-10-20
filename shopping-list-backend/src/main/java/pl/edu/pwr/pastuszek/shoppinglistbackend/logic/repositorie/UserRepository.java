package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.UserOrganization;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends SoftDeleteRepository<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNumber(String number);
    boolean existsByEmail(String email);
    boolean existsByNumber(String number);

    @Query("SELECT org FROM User u " +
            "JOIN u.userOrganizations uo " +
            "JOIN uo.organization org " +
            "WHERE u.id = :userId")
    List<Organization> findOrganizationsByUserId(@Param("userId") UUID userId);

    @Query("SELECT uo FROM User u " +
            "JOIN u.userOrganizations uo " +
            "WHERE u.id = :userId")
    List<UserOrganization> findUserOrganizationsByUserId(@Param("userId") UUID userId);
}
