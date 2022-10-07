package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Transaction;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface AccountService {
    Set<Transaction> accountTransactions(Long accountId);

    Double totalBalance(Principal principal);

    Account save(Account account);

    Account findById(Long id);

    List<Account> findAll();

    Account update(Long id, Account account);

    Boolean delete(Long id);
}