package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

/**
 * Base class for all entities.
 */

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DatabaseEntity databaseEntity = (DatabaseEntity) o;
        return getId() != null && Objects.equals(getId(), databaseEntity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
