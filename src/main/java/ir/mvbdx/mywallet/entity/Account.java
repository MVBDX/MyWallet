package ir.mvbdx.mywallet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ir.mvbdx.mywallet.enumeration.AccountType;
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
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private Set<Transaction> transactions;

    public void deposit(Double amount) {
        this.setBalance(this.getBalance() + amount);
    }

    public void withdraw(Double amount) {
        if (this.getBalance() >= amount) {
            this.setBalance(this.getBalance() - amount);
        } else throw new AccountException("Account balance is not enough!");
    }

}
