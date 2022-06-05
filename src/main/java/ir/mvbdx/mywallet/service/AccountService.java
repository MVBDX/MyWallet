package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.entity.Account;

import java.util.List;

public interface AccountService {
    Account save(Account account);

    Account findById(Long id);

    List<Account> findAll();

    Account update(Long id, Account account);

    boolean delete(Long id);
}
