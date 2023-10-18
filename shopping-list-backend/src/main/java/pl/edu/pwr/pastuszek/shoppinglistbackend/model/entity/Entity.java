package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Base interface for all entities.
 */


public interface Entity extends Serializable {
    UUID getId();
    void setId(UUID id);
}
