package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNumber(String number);
    boolean existsByEmail(String email);
    boolean existsByNumber(String number);
}
