package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "user", schema = "public", catalog = "shopping_list_db")
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "deleted = false")
public class User implements SoftDeleteEntity, UserDetails {
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
    @JsonIgnore
    private String password;
    @Enumerated(value = EnumType.STRING)
    @JsonIgnore
    private Role role = Role.USER;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<UserOrganization> userOrganizations;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<Invitation> invitations;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<Bill> bills;

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
                "email = " + email + ")";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }
}
