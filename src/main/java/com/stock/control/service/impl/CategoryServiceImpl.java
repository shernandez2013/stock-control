package com.stock.control.service.impl;

import com.stock.control.entities.Category;
import com.stock.control.exception.NotFoundException;
import com.stock.control.exception.ProcessingException;
import com.stock.control.repositories.CategoryRepository;
import com.stock.control.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        try {
            return categoryRepository.findAll();
        } catch (DataAccessException e) {
            log.error("Error while fetching all categories", e);
            throw new ProcessingException("An error occurred while fetching categories from the database", e);
        }
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("categories not found with id: " + id));
    }

    @Override
    @Transactional
    public Category save(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category information is missing");
        }
        if (categoryRepository.existsById(category.getCategoryId())) {
            throw new IllegalArgumentException("Category already exist: " + category.getCategoryId());
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category update(Long id, Category updatedCategory) {
        if (updatedCategory == null || updatedCategory.getCategoryId() == null) {
            throw new IllegalArgumentException("Category information is missing");
        }
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        existingCategory.setDescription(updatedCategory.getDescription());
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
        if (!categoryRepository.existsById(category.getCategoryId())) {
            throw new NotFoundException("Category not found " + category.getCategoryId());
        }
        categoryRepository.deleteById(id);
    }
}
