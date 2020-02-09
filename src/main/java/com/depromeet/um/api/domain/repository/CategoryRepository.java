package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAllByUpperCategoryCode(String upperCategoryCode);
    List<Category> findAllByUpperCategoryCodeNull();
    List<Category> findAllByUpperCategoryCodeNotNull();
}
