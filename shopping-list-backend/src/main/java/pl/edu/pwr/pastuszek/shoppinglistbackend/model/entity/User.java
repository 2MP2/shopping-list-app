package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Table(name = "user", schema = "public", catalog = "shopping_list_db")
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "deleted = false")
public class User implements SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
    private String name;
    private String surname;
    @Column(unique = true)
    private String number;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String login;
    @JsonIgnore
    private String password;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<UserOrganization> userOrganizations;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<Invitation> invitations;

    @PreRemove
    private void preDelete(){
        for (UserOrganization userOrg: userOrganizations) {
            userOrg.setDeleted(true);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "deleted = " + deleted + ", " +
                "name = " + name + ", " +
                "surname = " + surname + ", " +
                "number = " + number + ", " +
                "email = " + email + ", " +
                "login = " + login + ")";
    }
}
