package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.entity.Account;

import java.security.Principal;
import java.util.List;

public interface AccountService {
    Double totalBalance(Principal principal);

    Account save(Account account);

    Account findById(Long id);

    List<Account> findAll();

    Account update(Long id, Account account);

    void delete(Long id);
}