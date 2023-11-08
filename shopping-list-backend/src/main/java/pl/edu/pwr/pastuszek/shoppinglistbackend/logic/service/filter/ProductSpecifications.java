package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Product;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;

import java.util.Map;
import java.util.UUID;

@Component
public class ProductSpecifications implements CreatSpecifications<Product> {
    @Override
    public Specification<Product> creat(Map<String, String> params) {
        Specification<Product> spec = Specification.where(null);
        if(params.get("status")!=null)
            spec = spec.and(ProductSpecifications.withIsBought(Boolean.parseBoolean(params.get("isBought"))));
        if(params.get("shoppingList")!=null)
            spec = spec.and(ProductSpecifications.withShoppingListId(params.get("shoppingList")));
        if(params.get("bill")!=null)
            spec = spec.and(ProductSpecifications.withBillId(params.get("bill")));
        return spec;
    }

    public static Specification<Product> withIsBought(boolean isBought) {
        return (root, query, builder) -> builder.equal(root.get("status"), isBought);
    }

    public static Specification<Product> withShoppingListId(String shoppingListId) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, ShoppingList> shoppingListJoin = root.join("shoppingList");
            return criteriaBuilder.equal(shoppingListJoin.get("id"), UUID.fromString(shoppingListId));
        };
    }

    public static Specification<Product> withBillId(String billId) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Bill> billJoin = root.join("bill");
            return criteriaBuilder.equal(billJoin.get("id"), UUID.fromString(billId));
        };
    }


}
