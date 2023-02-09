package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.exception.DBException;
import ir.mvbdx.mywallet.model.entity.Category;
import ir.mvbdx.mywallet.repository.CategoryRepository;
import ir.mvbdx.mywallet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public static final String CLASS_NAME = Category.class.getSimpleName();
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new DBException.EntityNotFoundException(CLASS_NAME, id));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Long id, Category category) {
        categoryRepository.findById(id).orElseThrow(() -> new DBException.EntityNotFoundException(CLASS_NAME, id));
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new DBException.EntityNotFoundException(CLASS_NAME, id));
        if (!category.getTransactions().isEmpty() || !category.getSubordinates().isEmpty())
            throw new DBException.EntityHaveRelationException(CLASS_NAME + " " + category.getName(), id);
        categoryRepository.deleteById(id);
    }
}
