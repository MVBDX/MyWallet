package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.exception.EntityHaveRelationException;
import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import ir.mvbdx.mywallet.repository.AccountRepository;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.repository.TransactionRepository;
import ir.mvbdx.mywallet.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public String totalIncome(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return new DecimalFormat("#").format(transactionRepository.totalIncomeOfAccount(account.get().getId()));
    }

    public Double totalOutcome(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return transactionRepository.totalSpendOfAccount(account.get().getId());
    }

    @Override
    public Double totalBalance(Principal principal) {
        return accountRepository.totalBalance(customerRepository.findByEmail(principal.getName()));
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Account.class.getSimpleName()));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account update(Long id, Account account) {
        accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Account.class.getSimpleName()));
        return accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        Optional<Account> base = accountRepository.findById(id);
        if (base.isEmpty()) throw new EntityNotFoundException(id, Account.class.getSimpleName());
        if (!base.get().getTransactions().isEmpty())
            throw new EntityHaveRelationException(Account.class.getSimpleName() + " " + base.get().getName());
        accountRepository.deleteById(id);
    }

}
