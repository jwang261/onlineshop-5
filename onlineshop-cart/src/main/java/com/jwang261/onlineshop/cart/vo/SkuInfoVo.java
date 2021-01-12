package com.jwang261.onlineshop.cart.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jwang261
 * @date 2021/1/11 8:11 PM
 */
@Data
public class SkuInfoVo {
    private Long skuId;
    /**
     * $column.comments
     */
    private Long spuId;
    /**
     * $column.comments
     */
    private String skuName;
    /**
     * $column.comments
     */
    private String skuDesc;
    /**
     * $column.comments
     */
    private Long catalogId;
    /**
     * $column.comments
     */
    private Long brandId;
    /**
     * $column.comments
     */
    private String skuDefaultImg;
    /**
     * $column.comments
     */
    private String skuTitle;
    /**
     * $column.comments
     */
    private String skuSubtitle;
    /**
     * $column.comments
     */
    private BigDecimal price;
    /**
     * $column.comments
     */
    private Long saleCount;
}
