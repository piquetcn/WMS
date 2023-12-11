package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Category {

    @NotNull
    private Integer id;                     // 主键id
    @NotEmpty
    private String categoryName;            // 分类名称
    @NotEmpty
    @Pattern(regexp = "^[A-Z]{1,10}$")
    private String categoryAcronyms;        // 分类首字母简写
}
