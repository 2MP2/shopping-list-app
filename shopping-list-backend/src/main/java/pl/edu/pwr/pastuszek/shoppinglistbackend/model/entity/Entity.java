package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import java.util.UUID;

/**
 * Base interface for all entities.
 */


public interface Entity {
    UUID getId();
    void setId(UUID id);
}
