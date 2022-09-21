package ir.mvbdx.mywallet.entity;

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
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String number;
    private AccountType type;
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;
    private boolean deleted = Boolean.FALSE;

    public void deposit(Double amount) {
        this.setBalance(this.getBalance() + amount);
    }

    public void withdraw(Double amount) {
        if (this.getBalance() >= amount) {
            this.setBalance(this.getBalance() - amount);
        } else throw new AccountException("Account balance is not enough!");
    }

}
