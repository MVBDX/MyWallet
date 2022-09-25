package ir.mvbdx.mywallet.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link ir.mvbdx.mywallet.entity.Category} entity
 */
@Data
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    private Set<TransactionDTO> transactions;
    private CategoryDTO parentCategory;
    private Set<CategoryDTO> subordinates;
}