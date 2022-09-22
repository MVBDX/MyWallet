package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.entity.paging.Paged;
import ir.mvbdx.mywallet.entity.paging.Paging;
import ir.mvbdx.mywallet.enumeration.TransactionType;
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

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        Account account = transaction.getAccount();
        if (transaction.getType().equals(TransactionType.WITHDRAW))
            account.withdraw(transaction.getAmount());
        else if (transaction.getType().equals(TransactionType.DEPOSIT))
            account.deposit(transaction.getAmount());
        else if (transaction.getType().equals(TransactionType.TRANSFER)) {
            Account transferAccount = accountRepository.findById(transaction.getCategory().getId()).get();
            Transaction transferTransaction = new Transaction(TransactionType.DEPOSIT, transaction.getAmount(),
                    transferAccount, transaction.getCategory(), transaction.getAccount().getName(), null, transaction.getDate());
            account.withdraw(transaction.getAmount());
            transferAccount.deposit(transaction.getAmount());
            transaction.setType(TransactionType.WITHDRAW);
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
    public Double totalIncome() {
        return transactionRepository.totalIncome().orElse(0D);
    }

    @Override
    public Double totalSpend() {
        return transactionRepository.totalSpend().orElse(0D);
    }

    @Override
    public Double totalBalance() {
        return totalIncome() - totalSpend();
    }

    @Override
    public Paged<Transaction> findAllByCustomerOrderByDate(int pageNumber, int pageSize, Principal principal) {
        PageRequest request = PageRequest.of(pageNumber - 1, pageSize);
        Page<Transaction> transactionPage = transactionRepository.findAllByCustomer(customerRepository.findByEmail(principal.getName()), request);
        return new Paged<>(transactionPage, Paging.of(transactionPage.getTotalPages(), pageNumber, pageSize));
    }
}
