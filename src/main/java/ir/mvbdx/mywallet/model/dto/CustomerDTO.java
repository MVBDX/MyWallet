package ir.mvbdx.mywallet.model.dto;

import ir.mvbdx.mywallet.model.entity.Customer;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * A DTO for the {@link Customer} entity
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
//    private List<AccountDTO> accounts;
    private Collection<RoleDTO> roles;
}