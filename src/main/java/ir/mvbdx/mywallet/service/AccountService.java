package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.model.entity.Account;
import ir.mvbdx.mywallet.model.entity.Transaction;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface AccountService {
    Set<Transaction> getTransactions(Long accountId);

    Double totalBalance(Principal principal);

    Double totalBalanceWithoutCredits(Principal principal);

    Account save(Account account);

    Account findById(Long id);

    List<Account> findAll();

    Account update(Long id, Account account);

    Boolean delete(Long id);
}