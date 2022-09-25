package ir.mvbdx.mywallet.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * A DTO for the {@link ir.mvbdx.mywallet.entity.Customer} entity
 */
@Data
public class CustomerDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<AccountDTO> accounts;
    private Collection<RoleDTO> roles;
}