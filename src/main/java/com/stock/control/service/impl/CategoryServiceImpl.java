package com.stock.control.service.impl;

import com.stock.control.entities.Category;
import com.stock.control.repositories.CategoryRepository;
import com.stock.control.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> update(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(existingProduct -> {
            existingProduct.setCategoryName(updatedCategory.getCategoryName());
            existingProduct.setDescription(updatedCategory.getDescription());
            return categoryRepository.save(existingProduct);
        });
    }

    @Override
    public void deleteById(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Category id = " + id + " not found.");
        }
    }
}
