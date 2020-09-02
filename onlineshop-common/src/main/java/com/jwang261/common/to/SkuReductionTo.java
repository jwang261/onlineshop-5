package com.jwang261.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jwang261
 * @date 2020/8/29 1:20 PM
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
