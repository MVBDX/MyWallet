package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.entity.TransactionType;
import ir.mvbdx.mywallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction> {
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(@Qualifier("transactionRepository") JpaRepository<Transaction, Long> baseRepository) {
        super(baseRepository, "Transaction");
        transactionRepository = (TransactionRepository) baseRepository;
    }

    @Override
    public Transaction save(Transaction base) {
        Account account = base.getAccount();
        if (base.getType().equals(TransactionType.EXPENSE))
            account.setBalance(account.getBalance() - base.getAmount());
        else if (base.getType().equals(TransactionType.INCOME))
            account.setBalance(account.getBalance() + base.getAmount());
        // todo implement transfer between two account
        return super.save(base);
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
}
