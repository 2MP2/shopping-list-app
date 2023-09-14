package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
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
    private Timestamp creationDate;
    private Timestamp deleteDate;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<UserOrganization> userOrganizations;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Invitation> invitations;
}
