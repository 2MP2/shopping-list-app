package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;

import java.util.UUID;

@Repository
public interface ShoppingListRepository extends BaseRepository<ShoppingList> {
    @Query("SELECT s.organization.id FROM ShoppingList s WHERE s.id = :shoppingListId")
    UUID findOrganizationIdById(@Param("shoppingListId") UUID shoppingListId);
}
