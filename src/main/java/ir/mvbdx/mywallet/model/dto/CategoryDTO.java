package ir.mvbdx.mywallet.model.dto;

import ir.mvbdx.mywallet.model.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Category} entity
 */
@Data
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    private Set<TransactionDTO> transactions;
    private CategoryDTO parentCategory;
    private Set<CategoryDTO> subordinates;
}