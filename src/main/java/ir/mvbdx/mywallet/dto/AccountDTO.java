package ir.mvbdx.mywallet.dto;

import ir.mvbdx.mywallet.enumeration.AccountType;

import lombok.Data;

import java.util.Set;

@Data
public class AccountDTO {
    private Long id;
    private String name;
    private String number;
    private AccountType type;
    private Double balance;
    private CustomerDTO customer;
    private Set<TransactionDTO> transactions;
}
