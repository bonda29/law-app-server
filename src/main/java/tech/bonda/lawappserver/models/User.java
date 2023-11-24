package tech.bonda.lawappserver.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import tech.bonda.lawappserver.models.Role.Role;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotEmpty(message = "Username is mandatory!")
    @Column(name = "username", nullable = false)
    private String username;

    @NotEmpty(message = "Password is mandatory!")
    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @Column(name = "valid")
    private Boolean valid;
}
