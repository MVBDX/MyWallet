package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Transaction findById(Long id);

    List<Transaction> findAll();

    Transaction update(Long id, Transaction transaction);

    boolean delete(Long id);
}
