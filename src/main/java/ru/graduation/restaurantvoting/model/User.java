package ru.graduation.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
public class User extends BaseEntity implements Serializable {

    @Column(name = "first_name", nullable = false)
    @NotEmpty
    @Size(max = 128)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotEmpty
    @Size(max = 128)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty
    @Size(max = 128)
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 5, max = 128)
    private String password;

    @Column(name = "registered", columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;


    public User(Integer id, String firstName, String lastName, String email, String password, Role role, Role... roles) {
        this(id, firstName, lastName, email, password, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String firstName, String lastName, String email, String password, Date registered, Collection<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}
