package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Entity;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T extends Entity> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {}
