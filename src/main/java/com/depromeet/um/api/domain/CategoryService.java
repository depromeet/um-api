package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.Category;
import com.depromeet.um.api.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category findByCategoryCode(String categoryCode) {
        return categoryRepository.findById(categoryCode)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Category> findAllByCategoryCodes(List<String> categoryCodes) {
        return categoryRepository.findAllById(categoryCodes);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public List<Category> getAllUpperCategory() {
        return categoryRepository.findAllByUpperCategoryCodeNull();
    }

    public List<Category> getAllLowerCategory() {
        return categoryRepository.findAllByUpperCategoryCodeNotNull();
    }
    public Category getUpperCategory(Category category) {
        if(category.getUpperCategoryCode() == null) {
            return null;
        }
        return categoryRepository.findById(category.getUpperCategoryCode())
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Category> getLowerCategory(Category category) {
        return categoryRepository.findAllByUpperCategoryCode(category.getCategoryCode());
    }
}
