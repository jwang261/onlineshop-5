package com.jwang261.onlineshop.product.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:14:42
 */
@Data
public class AttrRespVo extends AttrVo implements Serializable {
	private String catelogName;
	private String groupName;
	private Long[] catelogPath;

}
