package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.exception.EntityHaveRelationException;
import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import ir.mvbdx.mywallet.repository.AccountRepository;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.repository.TransactionRepository;
import ir.mvbdx.mywallet.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static ir.mvbdx.mywallet.enumeration.TransactionType.DEPOSIT;
import static ir.mvbdx.mywallet.enumeration.TransactionType.WITHDRAW;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    public static final String CLASS_NAME = Account.class.getSimpleName();
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public Double totalIncome(Long accountId) {
        return transactionRepository.totalOfAccount(accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(accountId, CLASS_NAME)).getId(), DEPOSIT);
    }

    public Double totalOutcome(Long accountId) {
        return transactionRepository.totalOfAccount(accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(accountId, CLASS_NAME)).getId(), WITHDRAW);
    }

    @Override
    public Set<Transaction> getTransactions(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() ->
                new EntityNotFoundException(accountId, CLASS_NAME)).getTransactions();
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
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, CLASS_NAME));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account update(Long id, Account account) {
        accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, CLASS_NAME));
        return accountRepository.save(account);
    }

    @Override
    public Boolean delete(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, CLASS_NAME));
        if (!account.getTransactions().isEmpty())
            throw new EntityHaveRelationException(CLASS_NAME + " " + account.getName(), id);
        accountRepository.deleteById(id);
        return Boolean.TRUE;
    }

}
