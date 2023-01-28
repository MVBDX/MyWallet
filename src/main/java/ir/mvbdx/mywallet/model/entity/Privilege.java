package ir.mvbdx.mywallet.model.entity;

import ir.mvbdx.mywallet.model.enums.CustomerPrivilege;
import lombok.*;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilege extends BaseEntity {
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerPrivilege permission;
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}