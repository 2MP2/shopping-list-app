package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "organization", schema = "public", catalog = "shopping_list_db")
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Organization implements SoftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;
    @OneToMany(mappedBy = "organization")
    @ToString.Exclude
    @JsonIgnore
    private List<ShoppingList> shoppingLists;
    @OneToMany(mappedBy = "organization")
    @ToString.Exclude
    @JsonIgnore
    private List<Invitation> invitations;
    @OneToMany(mappedBy = "organization")
    @ToString.Exclude
    @JsonIgnore
    private List<UserOrganization> userOrganizations;
}
