package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.exception.EntityHaveRelationException;
import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import ir.mvbdx.mywallet.repository.CategoryRepository;
import ir.mvbdx.mywallet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Category.class.getSimpleName()));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Long id, Category category) {
        categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Category.class.getSimpleName()));
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty())
            throw new EntityNotFoundException(id, Category.class.getSimpleName());
        if (category.get().getTransactions().isEmpty() && category.get().getSubordinates().isEmpty())
            categoryRepository.deleteById(id);
        else
            throw new EntityHaveRelationException(Category.class.getSimpleName() + " " + category.get().getName());
    }
}
