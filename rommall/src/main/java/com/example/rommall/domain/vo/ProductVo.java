package com.example.rommall.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVo {
    private Integer id;
    private String code;
    private String title;
    private Integer categoryId;
    private String categoryName;
    private String img;
    private BigDecimal price;
    private Integer stocks;
    private String description;
}
