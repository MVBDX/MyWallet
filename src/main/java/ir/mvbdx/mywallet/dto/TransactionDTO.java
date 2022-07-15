package ir.mvbdx.mywallet.dto;

import ir.mvbdx.mywallet.enumeration.TransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private AccountDTO account;
    private CategoryDTO category;
    private String name;
    private String note;
    private Date date;
}
