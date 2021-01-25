package com.jwang261.onlineshop.order.vo;

import lombok.Data;

/**
 * @author jwang261
 * @date 2021/1/25 11:25 AM
 */
@Data
public class SkuStockVo {
    private Long skuId;
    private Boolean hasStock;
}
