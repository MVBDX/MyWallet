package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.entity.paging.Paged;
import ir.mvbdx.mywallet.entity.paging.Paging;
import ir.mvbdx.mywallet.enumeration.TransactionType;
import ir.mvbdx.mywallet.repository.AccountRepository;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction> {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(@Qualifier("transactionRepository") JpaRepository<Transaction, Long> baseRepository) {
        super(baseRepository, "Transaction");
        transactionRepository = (TransactionRepository) baseRepository;
    }

    @Override
    public Transaction save(Transaction base) {
        Account account = base.getAccount();
        if (base.getType().equals(TransactionType.WITHDRAW))
            account.withdraw(base.getAmount());
        else if (base.getType().equals(TransactionType.DEPOSIT))
            account.deposit(base.getAmount());
        else if (base.getType().equals(TransactionType.TRANSFER)) {
            Account transferAccount = accountRepository.findById(base.getCategory().getId()).get();
            Transaction transferTransaction = new Transaction(null, TransactionType.DEPOSIT, base.getAmount(),
                    transferAccount, base.getCategory(), base.getAccount().getName(), null, base.getDate(),
                    null, null, false);
            account.withdraw(base.getAmount());
            transferAccount.deposit(base.getAmount());
            base.setType(TransactionType.WITHDRAW);
            super.save(transferTransaction);
        }
        return super.save(base);
    }

    @Override
    public Transaction update(Long id, Transaction base) {
        return super.update(id, base);
    }

    public Double totalIncome() {
        Double result = transactionRepository.totalIncome();
        return (result != null) ? result : 0D;
    }

    public Double totalSpend() {
        Double result = transactionRepository.totalSpend();
        return (result != null) ? result : 0D;
    }

    public Double totalBalance() {
        try {
            return totalIncome() - totalSpend();
        } catch (Exception e) {
            return 0D;
        }
    }

    public Paged<Transaction> findAllByCustomerOrderByDate(int pageNumber, int pageSize, Principal principal) {
        PageRequest request = PageRequest.of(pageNumber - 1, pageSize);
        Page<Transaction> transactionPage = transactionRepository.findAllByCustomer(customerRepository.findByEmail(principal.getName()), request);
        return new Paged<>(transactionPage, Paging.of(transactionPage.getTotalPages(), pageNumber, pageSize));
    }
}
