package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;

import java.util.UUID;

@Repository
public interface BillRepository extends BaseRepository<Bill>{
    @Query("SELECT b.shoppingList.organization.id FROM Bill b WHERE b.id = :billId")
    UUID findOrganizationIdById(@Param("billId") UUID billId);
}
