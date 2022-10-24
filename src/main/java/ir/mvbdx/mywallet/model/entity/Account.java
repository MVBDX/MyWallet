package ir.mvbdx.mywallet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ir.mvbdx.mywallet.model.enums.AccountType;
import ir.mvbdx.mywallet.exception.AccountException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE account SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
    private String name;
    private String number;
    private AccountType type;
    private Double balance;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @JsonManagedReference
    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

    public synchronized void deposit(Double amount) {
        balance += amount;
    }

    public synchronized void withdraw(Double amount) {
        if (balance < amount)
            throw new AccountException("Account balance is not enough!");
        balance -= amount;
    }

}
