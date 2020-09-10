package com.jwang261.onlineshop.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/9/6 3:19 PM
 * keyword=**&catalog3Id=**&sort=saleCount_asc&hasStock=0/1&brandId=A&brandId=B
 * &attrs=1_others:Android&attrs=2_4G:8G
 */
@Data
public class SearchParamVo {

    private String keyword;
    private Long catalog3Id;

    /**
     * sort=saleCount_asc/desc
     * sort=skuPrice_asc/desc
     * sort=hotScore_asc/desc
     */
    private String sort;

    /**
     * filter:
     * hasStock, skuPrice range, brandId, catalog3Id, attrs
     * 0/1
     */
    private Integer hasStock;
    private String skuPrice;
    private List<Long> brandId;
    private List<String> attrs;
    private Integer pageNum = 10;
}
