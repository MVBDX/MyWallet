package ir.mvbdx.mywallet.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Set<TransactionDTO> transactions;
    private CategoryDTO parentCategory;
    private Set<CategoryDTO> subordinates = new HashSet<>();
}
