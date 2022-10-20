package ir.mvbdx.mywallet.model.dto;

import ir.mvbdx.mywallet.model.enums.AccountType;
import ir.mvbdx.mywallet.model.entity.Account;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Account} entity
 */
@Data
public class AccountDTO implements Serializable {
    private Long id;
    private String name;
    private String number;
    private AccountType type;
    private Double balance;
    private CustomerDTO customer;
//    private Set<TransactionDTO> transactions;
}