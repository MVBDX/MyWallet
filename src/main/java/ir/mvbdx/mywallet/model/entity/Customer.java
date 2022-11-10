package ir.mvbdx.mywallet.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ir.mvbdx.mywallet.model.enums.CustomerStatus;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@SQLDelete(sql = "UPDATE customer SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity {
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "{label.pages.register.password.error}")
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    private String phone;
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Account> accounts;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
    @Transient
    private Set<Privilege> privileges;
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    public Set<Privilege> getPrivileges() {
        return this.roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
