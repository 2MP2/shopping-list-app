package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.SoftDeleteEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends SoftDeleteEntity> extends BaseRepository<T>{

    @Query("select e from #{#entityName} e where e.deleted = false")
    @Override
    List<T> findAll();

    @Query("select e from #{#entityName} e where e.deleted = false and e.id = ?1")
    @Override
    Optional<T> findById(@NonNull UUID id);

    @Query("select count(e)>0 from #{#entityName} e where e.id = ?1 and e.deleted = false")
    @Override
    boolean existsById(@NonNull UUID id);

    @Query("select e from #{#entityName} e where e.deleted = true")
    List<T> findAllDeleted();

    @Modifying
    @Query("update #{#entityName} e set e.deleted = true where e.id = ?1")
    @Override
    void deleteById(@NonNull UUID id);

    @Modifying
    @Query("update #{#entityName} e set e.deleted = true where e.id in ?1")
    void deleteAllById(@NonNull Iterable<? extends UUID> ids);
}
