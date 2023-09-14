package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Table(name = "bill", schema = "public", catalog = "shopping_list_db")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Bill extends DatabaseEntity {
    private int amount;
    private String shop;
    private String comment;
    @OneToMany(mappedBy = "bill")
    @ToString.Exclude
    private List<Product> products;
}
