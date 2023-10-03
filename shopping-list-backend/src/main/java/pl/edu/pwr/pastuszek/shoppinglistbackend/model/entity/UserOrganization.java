package pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "user_organization", schema = "public", catalog = "shopping_list_db")
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class UserOrganization implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;
    @Enumerated(value = EnumType.STRING)
    private UserOrganizationStatus status;
}
