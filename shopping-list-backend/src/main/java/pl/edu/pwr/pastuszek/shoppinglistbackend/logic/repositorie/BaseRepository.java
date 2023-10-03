package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Entity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused") // future feature
@NoRepositoryBean
public interface BaseRepository<T extends Entity> extends JpaRepository<T, UUID> {
    default List<T> getByNaturalId(String keyValue, String ... keyName) {
        return Collections.emptyList();
    }

    default boolean supportsNaturalId() {
        return false;
    }
}
