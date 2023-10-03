package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Table(name = "user", schema = "public", catalog = "shopping_list_db")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class User extends DatabaseEntity {
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
