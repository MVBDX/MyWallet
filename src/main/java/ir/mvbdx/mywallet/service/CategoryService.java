package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    Category findById(Long id);

    List<Category> findAll();

    Category update(Long id, Category category);

    void delete(Long id);
}