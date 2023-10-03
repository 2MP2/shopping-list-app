package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "bill", schema = "public", catalog = "shopping_list_db")
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Bill implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int amount;
    private String shop;
    private String comment;
    @OneToMany(mappedBy = "bill")
    @ToString.Exclude
    @JsonIgnore
    private List<Product> products;
}
