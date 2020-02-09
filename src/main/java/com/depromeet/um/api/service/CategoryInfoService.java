package com.depromeet.um.api.service;

import com.depromeet.um.api.domain.CategoryService;
import com.depromeet.um.api.domain.model.Category;
import com.depromeet.um.api.dto.CategoryInfo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CategoryInfoService {

    private final CategoryService categoryService;
    private static CategoryInfo categoryInfo = CategoryInfo.builder().build();
    private static Set<String> categoryCodeSet = Sets.newHashSet();

    public CategoryInfoService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Scheduled(fixedRate = 300000)
    public void scheduledCategoryInfo() {
        log.info("scheduledCategoryInfo");
        updateCategoryInfo();
    }

    private void updateCategoryInfo() {
        Map<String, CategoryInfo> categoryInfoMap = Maps.newHashMap();
        List<Category> categories = categoryService.findAll();
        categories.forEach(
                category -> {
                    categoryCodeSet.add(category.getCategoryCode());
                    if (category.getUpperCategoryCode() == null) { // 최상위 카테고리 일시
                        if (categoryInfoMap.containsKey(category.getCategoryCode())) {
                            categoryInfoMap.get(category.getCategoryCode())
                                    .setCategoryCode(category.getCategoryCode());
                        } else {
                            categoryInfoMap.put(category.getCategoryCode(), CategoryInfo.builder()
                                    .categoryCode(category.getCategoryCode())
                                    .categoryInfos(Sets.newHashSet())
                                    .build());
                        }
                    } else {
                        if (categoryInfoMap.containsKey(category.getUpperCategoryCode())) { // 맵에 이미 있는
                            categoryInfoMap.get(category.getUpperCategoryCode()).getCategoryInfos()
                                    .add(CategoryInfo.builder()
                                            .categoryCode(category.getCategoryCode())
                                            .build());
                        } else {
                            categoryInfoMap.put(category.getUpperCategoryCode(), CategoryInfo.builder()
                                    .categoryCode(category.getUpperCategoryCode())
                                    .categoryInfos(Sets.newHashSet())
                                    .build());
                        }
                    }
                }
        );
        categoryInfo = CategoryInfo.builder()
                .categoryInfos(Sets.newHashSet(categoryInfoMap.values()))
                .build();
    }

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public static boolean checkCategoryCode(String categoryCode) {
        return categoryCodeSet.contains(categoryCode);
    }
}
