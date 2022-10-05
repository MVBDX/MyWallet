package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import ir.mvbdx.mywallet.repository.AccountRepository;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.repository.TransactionRepository;
import ir.mvbdx.mywallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static ir.mvbdx.mywallet.enumeration.TransactionType.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        Account account = transaction.getAccount();
        if (transaction.getType().equals(WITHDRAW))
            account.withdraw(transaction.getAmount());
        else if (transaction.getType().equals(DEPOSIT))
            account.deposit(transaction.getAmount());
        else if (transaction.getType().equals(TRANSFER)) {
            Account transferAccount = accountRepository.findById(transaction.getCategory().getId()).get();
            Transaction transferTransaction = new Transaction(DEPOSIT, transaction.getAmount(),
                    transferAccount, transaction.getCategory(), transaction.getAccount().getName(), null, transaction.getDate());
            account.withdraw(transaction.getAmount());
            transferAccount.deposit(transaction.getAmount());
            transaction.setType(WITHDRAW);
            transactionRepository.save(transferTransaction);
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Transaction.class.getSimpleName()));
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction update(Long id, Transaction transaction) {
        transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Transaction.class.getSimpleName()));
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Transaction.class.getSimpleName()));
        transactionRepository.deleteById(id);
    }

    @Override
    public Double totalIncome(Principal principal) {
        return transactionRepository.totalAmount(customerRepository.findByEmail(principal.getName()), DEPOSIT).orElse(0D);
    }

    @Override
    public Double totalSpend(Principal principal) {
        return transactionRepository.totalAmount(customerRepository.findByEmail(principal.getName()), WITHDRAW).orElse(0D);
    }

    @Override
    public Double totalBalance(Principal principal) {
        return totalIncome(principal) - totalSpend(principal);
    }

    @Override
    public Page<Transaction> findAllByCustomerOrderByDate(int pageNumber, int pageSize, Principal principal) {
        return transactionRepository.findAllByCustomer(customerRepository.findByEmail(principal.getName()),
                PageRequest.of(pageNumber - 1, pageSize));
    }
}
