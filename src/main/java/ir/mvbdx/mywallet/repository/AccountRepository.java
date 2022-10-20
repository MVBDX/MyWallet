package ir.mvbdx.mywallet.repository;

import ir.mvbdx.mywallet.model.entity.Account;
import ir.mvbdx.mywallet.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT SUM(a.balance) FROM Account AS a WHERE a.customer = ?1")
    Double totalBalance(Customer customer);

}
