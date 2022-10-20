package ir.mvbdx.mywallet.repository;

import ir.mvbdx.mywallet.model.entity.Customer;
import ir.mvbdx.mywallet.model.entity.Transaction;
import ir.mvbdx.mywallet.model.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction AS t WHERE t.type = :transactionType AND t.account.id = :accountId")
    Double totalOfAccount(Long accountId, TransactionType transactionType);

    @Query("SELECT SUM(t.amount) FROM Transaction AS t, Account AS a WHERE t.type = :transactionType AND t.account = a AND a.customer = :customer")
    Optional<Double> totalAmount(Customer customer, TransactionType transactionType);

    Page<Transaction> findAllByAccount_CustomerOrderByDateDescIdDesc(Customer account_customer, Pageable pageable);

}
