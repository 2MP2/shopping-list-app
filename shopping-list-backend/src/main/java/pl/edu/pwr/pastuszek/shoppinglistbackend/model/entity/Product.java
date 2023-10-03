package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "product", schema = "public", catalog = "shopping_list_db")
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Product implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int quantity;
    private String status;
    @ManyToOne
    @JoinColumn(name = "shopping_list_id", referencedColumnName = "id")
    private ShoppingList shoppingList;
    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "id")
    private Bill bill;
}
