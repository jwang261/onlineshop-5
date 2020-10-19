package com.jwang261.onlineshop.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/10/18 10:10 PM
 */
@Data
@ToString
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<String> attrValues;
}
