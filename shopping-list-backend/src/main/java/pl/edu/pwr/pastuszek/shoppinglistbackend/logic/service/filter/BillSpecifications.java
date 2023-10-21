package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;

import java.util.Map;
import java.util.UUID;

@Component
public class BillSpecifications implements CreatSpecifications<Bill>{

    @Override
    public Specification<Bill> creat(Map<String, String> params) {
        Specification<Bill> spec = Specification.where(null);
        if(params.get("amount")!=null)
            spec = spec.and(BillSpecifications.withAmount(params.get("amount")));
        if(params.get("shop")!=null)
            spec = spec.and(BillSpecifications.withShop(params.get("shop")));
        if(params.get("shoppingList")!=null)
            spec = spec.and(BillSpecifications.withShoppingListId(params.get("shoppingList")));
        if(params.get("user")!=null)
            spec = spec.and(BillSpecifications.withUserId((params.get("user"))));
        return spec;
    }

    public static Specification<Bill> withAmount(String amount) {
        return (root, query, builder) -> builder.equal(root.get("amount"), amount);
    }

    public static Specification<Bill> withShop(String shop) {
        return (root, query, builder) -> builder.equal(root.get("shop"), shop);
    }

    public static Specification<Bill> withShoppingListId(String shoppingListId) {
        return (root, query, criteriaBuilder) -> {
            Join<Bill, ShoppingList> shoppingListJoin = root.join("shoppingList");
            return criteriaBuilder.equal(shoppingListJoin.get("id"), UUID.fromString(shoppingListId));
        };
    }

    public static Specification<Bill> withUserId(String userId) {
        return (root, query, criteriaBuilder) -> {
            Join<Bill, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("id"), UUID.fromString(userId));
        };
    }
}
