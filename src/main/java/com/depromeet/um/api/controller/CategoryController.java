package com.depromeet.um.api.controller;

import com.depromeet.um.api.dto.CategoryInfo;
import com.depromeet.um.api.service.CategoryInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryInfoService categoryInfoService;

    public CategoryController(CategoryInfoService categoryInfoService) {
        this.categoryInfoService = categoryInfoService;
    }

    @ApiOperation(value = "Get all category", notes = "하위 카테고리 까지 가져온다")
    @GetMapping
    public ResponseEntity<CategoryInfo> getCategoryInfo() {
        return ResponseEntity.ok(categoryInfoService.getCategoryInfo());
    }
}
