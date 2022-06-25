package ir.mvbdx.mywallet.repository;

import ir.mvbdx.mywallet.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT SUM(a.balance) FROM Account AS a WHERE a.deleted = false ")
    Double totalBalance();

}
