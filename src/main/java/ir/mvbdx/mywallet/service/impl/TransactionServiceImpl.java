package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction> {
    public TransactionServiceImpl(@Qualifier("transactionRepository") JpaRepository<Transaction, Long> baseRepository) {
        super(baseRepository, "Transaction");
    }
}
