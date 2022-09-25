package ir.mvbdx.mywallet.dto;

import ir.mvbdx.mywallet.enumeration.AccountType;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link ir.mvbdx.mywallet.entity.Account} entity
 */
@Data
public class AccountDTO implements Serializable {
    private Long id;
    private String name;
    private String number;
    private AccountType type;
    private Double balance;
    private CustomerDTO customer;
    private Set<TransactionDTO> transactions;
}