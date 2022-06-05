package ir.mvbdx.mywallet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@SQLDelete(sql = "UPDATE transaction SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TransactionType type;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private Category category;
    private String note;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private boolean deleted = Boolean.FALSE;
}
