package com.jwang261.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jwang261
 * @date 2020/8/29 12:28 PM
 */
@Data
public class SpuBoundsTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
