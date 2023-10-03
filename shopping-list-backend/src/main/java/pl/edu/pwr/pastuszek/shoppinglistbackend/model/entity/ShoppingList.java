package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "shopping_list", schema = "public", catalog = "shopping_list_db")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class ShoppingList extends DatabaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;
    @OneToMany(mappedBy = "shoppingList")
    @ToString.Exclude
    @JsonIgnore
    private List<Product> products;
}
