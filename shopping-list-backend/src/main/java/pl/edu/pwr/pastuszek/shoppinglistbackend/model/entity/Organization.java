package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "organization", schema = "public", catalog = "shopping_list_db")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Organization extends DatabaseEntity {
    private String name;
    private Timestamp creationDate;
    private Timestamp deleteDate;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;
    @OneToMany(mappedBy = "organization")
    @ToString.Exclude
    private List<ShoppingList> shoppingLists;
    @OneToMany(mappedBy = "organization")
    @ToString.Exclude
    private List<Invitation> invitations;
    @OneToMany(mappedBy = "organization")
    @ToString.Exclude
    private List<UserOrganization> userOrganizations;
}
