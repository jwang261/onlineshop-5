package com.jwang261.common.to;

import lombok.Data;

/**
 * @author jwang261
 * @date 2020/8/31 8:23 PM
 */
@Data
public class SkuHasStockVo {
    private Long skuId;
    private Boolean hasStock;
}
