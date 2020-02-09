package com.depromeet.um.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Category {
    @Id
    @NotNull
    private String categoryCode;

    /**
     * 상위 카테고리 code 이다.
     */
    @Nullable
    private String upperCategoryCode;

    @Nullable
    private String description;

}
