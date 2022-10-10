package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.exception.EntityHaveRelationException;
import ir.mvbdx.mywallet.exception.EntityNotFoundException;
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
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, CLASS_NAME));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Long id, Category category) {
        categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, CLASS_NAME));
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, CLASS_NAME));
        if (!category.getTransactions().isEmpty() || !category.getSubordinates().isEmpty())
            throw new EntityHaveRelationException(CLASS_NAME + " " + category.getName(), id);
        categoryRepository.deleteById(id);
    }
}
