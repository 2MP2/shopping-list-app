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
    public static final String AMOUNT = "amount";
    public static final String SHOP = "shop";
    public static final String SHOPPING_LIST = "shoppingList";
    public static final String USER = "user";

    @Override
    public Specification<Bill> creat(Map<String, String> params) {
        Specification<Bill> spec = Specification.where(null);
        if(params.get(AMOUNT)!=null)
            spec = spec.and(BillSpecifications.withAmount(params.get(AMOUNT)));
        if(params.get(SHOP)!=null)
            spec = spec.and(BillSpecifications.withShop(params.get(SHOP)));
        if(params.get(SHOPPING_LIST)!=null)
            spec = spec.and(BillSpecifications.withShoppingListId(params.get(SHOPPING_LIST)));
        if(params.get(USER)!=null)
            spec = spec.and(BillSpecifications.withUserId((params.get(USER))));
        return spec;
    }

    public static Specification<Bill> withAmount(String amount) {
        return (root, query, builder) -> builder.equal(root.get(AMOUNT), amount);
    }

    public static Specification<Bill> withShop(String shop) {
        return (root, query, builder) -> builder.equal(root.get(SHOP), shop);
    }

    public static Specification<Bill> withShoppingListId(String shoppingListId) {
        return (root, query, criteriaBuilder) -> {
            Join<Bill, ShoppingList> shoppingListJoin = root.join(SHOPPING_LIST);
            return criteriaBuilder.equal(shoppingListJoin.get("id"), UUID.fromString(shoppingListId));
        };
    }

    public static Specification<Bill> withUserId(String userId) {
        return (root, query, criteriaBuilder) -> {
            Join<Bill, User> userJoin = root.join(USER);
            return criteriaBuilder.equal(userJoin.get("id"), UUID.fromString(userId));
        };
    }
}
