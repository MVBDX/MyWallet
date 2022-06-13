package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> {
    public CategoryServiceImpl(@Qualifier("categoryRepository") JpaRepository<Category, Long> baseRepository) {
        super(baseRepository, "Category");
    }
}
