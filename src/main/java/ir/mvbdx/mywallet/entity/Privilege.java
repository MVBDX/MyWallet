package ir.mvbdx.mywallet.entity;

import ir.mvbdx.mywallet.enumeration.CustomerPrivilege;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege extends BaseEntity {
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerPrivilege permission;
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}