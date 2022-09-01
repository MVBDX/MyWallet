package ir.mvbdx.mywallet.repository;

import ir.mvbdx.mywallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    List<Transaction> findByOrderByDateDescIdDesc();

}
