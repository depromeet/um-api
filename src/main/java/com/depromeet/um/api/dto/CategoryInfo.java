package com.depromeet.um.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = {"categoryCode"})
@NoArgsConstructor(access = PROTECTED)
public class CategoryInfo {
    @Nullable
    @JsonInclude(NON_NULL)
    @Setter
    String categoryCode;
    @Nullable
    @JsonInclude(NON_NULL)
    Set<CategoryInfo> categoryInfos;
}
