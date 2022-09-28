package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.entity.Transaction;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface TransactionService {
    Double totalIncome();

    Double totalSpend();

    Double totalBalance();

    Page<Transaction> findAllByCustomerOrderByDate(int pageNumber, int pageSize, Principal principal);

    Transaction save(Transaction transaction);

    Transaction findById(Long id);

    List<Transaction> findAll();

    Transaction update(Long id, Transaction transaction);

    void delete(Long id);
}