package ir.mvbdx.mywallet.model.dto;

import ir.mvbdx.mywallet.model.enums.TransactionType;
import ir.mvbdx.mywallet.model.entity.Transaction;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link Transaction} entity
 */
@Data
public class TransactionDTO implements Serializable {
    private Long id;
    private TransactionType type;
    private Double amount;
//    private AccountDTO account;
//    private CategoryDTO category;
    private String name;
    private String note;
    private Date date;
}