package ir.mvbdx.mywallet.repository;

import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction AS t WHERE t.type = 1 AND t.account.id = ?1")
    Double totalIncomeOfAccount(Long accountId);

    @Query("SELECT SUM(t.amount) FROM Transaction AS t WHERE t.type = 0 AND t.account.id = ?1")
    Double totalSpendOfAccount(Long accountId);

    @Query("SELECT SUM(t.amount) FROM Transaction AS t WHERE t.type = 1")
    Double totalIncome();

    @Query("SELECT SUM(t.amount) FROM Transaction AS t WHERE t.type = 0")
    Double totalSpend();

    @Query("SELECT t FROM Transaction t, Account a WHERE t.account = a AND a.customer = :customer ORDER BY t.date DESC, t.id DESC")
    List<Transaction> findAllByCustomer(@Param("customer") Customer customer);

}
