package com.jwang261.onlineshop.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 秒杀活动商品关联
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:45:11
 */
@Data
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long id;
	/**
	 * $column.comments
	 */
	private Long promotionId;
	/**
	 * $column.comments
	 */
	private Long promotionSessionId;
	/**
	 * $column.comments
	 */
	private Long skuId;
	/**
	 * $column.comments
	 */
	private BigDecimal seckillPrice;
	/**
	 * $column.comments
	 */
	private BigDecimal seckillCount;
	/**
	 * $column.comments
	 */
	private BigDecimal seckillLimit;
	/**
	 * $column.comments
	 */
	private Integer seckillSort;

}
