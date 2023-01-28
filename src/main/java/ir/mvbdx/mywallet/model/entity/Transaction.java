package ir.mvbdx.mywallet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ir.mvbdx.mywallet.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
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
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "account_id")
//    @NotNull
    private Account account;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String name;
    private String note;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Selected date is in future!")
    private Date date;
}
