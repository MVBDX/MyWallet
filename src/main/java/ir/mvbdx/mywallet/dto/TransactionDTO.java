package ir.mvbdx.mywallet.dto;

import ir.mvbdx.mywallet.enumeration.TransactionType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link ir.mvbdx.mywallet.entity.Transaction} entity
 */
@Data
public class TransactionDTO implements Serializable {
    private Long id;
    private TransactionType type;
    private Double amount;
    private AccountDTO account;
    private CategoryDTO category;
    private String name;
    private String note;
    private Date date;
}