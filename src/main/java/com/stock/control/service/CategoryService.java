package com.stock.control.service;

import com.stock.control.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    Category update(Long id, Category category);

    void deleteById(Long id);
}
