package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;

import java.util.UUID;

@Repository
public interface ProductRepository extends BaseRepository<Product> {
    @Query("SELECT p.shoppingList.organization.id FROM Product p WHERE p.id = :productId")
    UUID findOrganizationIdById(@Param("productId") UUID productId);
}
