package ir.mvbdx.mywallet.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerDTO {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Set<AccountDTO> accounts;
}
