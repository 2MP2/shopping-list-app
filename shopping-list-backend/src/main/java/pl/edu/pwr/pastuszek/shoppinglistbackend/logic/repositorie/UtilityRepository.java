package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

// User entity is unrelated to the procedure goal but Spring requires some managed entity to be
// parameter of JpaRepository
@Repository
public interface UtilityRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(value = "CALL seed_db();", nativeQuery = true)
    void resetDatabaseContents();
}
