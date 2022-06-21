package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.exception.EntityHaveRelationException;
import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import ir.mvbdx.mywallet.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(@Qualifier("categoryRepository") JpaRepository<Category, Long> baseRepository) {
        super(baseRepository, "Category");
        categoryRepository = (CategoryRepository) baseRepository;
    }

    @Override
    public void delete(Long id) {
        Optional<Category> base = categoryRepository.findById(id);
        if (base.isEmpty())
            throw new EntityNotFoundException(id, entityName);
        if (base.get().getTransactions().isEmpty() && base.get().getSubordinates().isEmpty())
            baseRepository.deleteById(id);
        else
            throw new EntityHaveRelationException(entityName + " " + base.get().getName());
    }
}
