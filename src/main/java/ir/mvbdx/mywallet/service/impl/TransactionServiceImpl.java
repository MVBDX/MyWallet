package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.entity.TransactionType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction> {
    public TransactionServiceImpl(@Qualifier("transactionRepository") JpaRepository<Transaction, Long> baseRepository) {
        super(baseRepository, "Transaction");
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
}
