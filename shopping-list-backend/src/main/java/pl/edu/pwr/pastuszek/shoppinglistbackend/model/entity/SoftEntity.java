package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

/**
 * Base interface for all entities with soft delete.
 */

public interface SoftEntity extends Entity{
    void setDeleted(boolean deleted);
    boolean isDeleted();
}
