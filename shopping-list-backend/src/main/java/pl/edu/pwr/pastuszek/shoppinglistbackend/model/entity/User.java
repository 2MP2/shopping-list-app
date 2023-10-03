package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "user", schema = "public", catalog = "shopping_list_db")
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class User implements SoftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;
    private String name;
    private String surname;
    private String number;
    private String email;
    private String login;
    private String password;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<UserOrganization> userOrganizations;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<Invitation> invitations;
}
