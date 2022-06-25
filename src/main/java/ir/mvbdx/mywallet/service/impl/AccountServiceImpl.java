package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.exception.EntityHaveRelationException;
import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import ir.mvbdx.mywallet.repository.AccountRepository;
import ir.mvbdx.mywallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account> {
    @Autowired
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    public AccountServiceImpl(@Qualifier("accountRepository") JpaRepository<Account, Long> baseRepository) {
        super(baseRepository, "Account");
        accountRepository = (AccountRepository) baseRepository;
    }

    public String totalIncome(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return new DecimalFormat("#").format(transactionRepository.totalIncomeOfAccount(account.get().getId()));
    }

    public Double totalOutcome(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return transactionRepository.totalSpendOfAccount(account.get().getId());
    }

    public Double totalBalance() {
        return accountRepository.totalBalance();
    }

    @Override
    public void delete(Long id) {
        Optional<Account> base = accountRepository.findById(id);
        if (base.isEmpty())
            throw new EntityNotFoundException(id, entityName);
        if (base.get().getTransactions().isEmpty())
            baseRepository.deleteById(id);
        else
            throw new EntityHaveRelationException(entityName + " " + base.get().getName());
    }

}
