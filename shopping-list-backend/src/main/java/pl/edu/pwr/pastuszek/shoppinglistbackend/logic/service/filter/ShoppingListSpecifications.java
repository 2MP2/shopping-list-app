package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Organization;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;

import java.util.Map;
import java.util.UUID;

@Component
public class ShoppingListSpecifications implements CreatSpecifications<ShoppingList> {
    @Override
    public Specification<ShoppingList> creat(Map<String, String> params) {
        Specification<ShoppingList> spec = Specification.where(null);
        if(params.get("organization")!=null)
            spec = spec.and(ShoppingListSpecifications.withOrganizationId((params.get("organization"))));
        if(params.get("bill")!=null)
            spec = spec.and(ShoppingListSpecifications.withBillId((params.get("bill"))));
        return spec;
    }

    public static Specification<ShoppingList> withOrganizationId(String organizationId) {
        return (root, query, builder) -> {
            Join<ShoppingList, Organization> organizationJoin = root.join("organization");
            return builder.equal(organizationJoin.get("id"), UUID.fromString(organizationId));
        };
    }

    public static Specification<ShoppingList> withBillId(String billId) {
        return (root, query, builder) -> {
            Join<ShoppingList, Bill> billJoin = root.join("bills");
            return builder.equal(billJoin.get("id"), UUID.fromString(billId));
        };
    }
}
