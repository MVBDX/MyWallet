package ir.mvbdx.mywallet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ir.mvbdx.mywallet.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@SQLDelete(sql = "UPDATE transaction SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {
    private TransactionType type;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
//    @NotNull
    private Account account;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
    private String name;
    private String note;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
