package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account> {
    public AccountServiceImpl(@Qualifier("accountRepository") JpaRepository<Account, Long> baseRepository) {
        super(baseRepository);
    }
}
