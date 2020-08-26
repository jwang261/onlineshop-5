package com.jwang261.onlineshop.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.jwang261.common.valid.AddGroup;
import com.jwang261.common.valid.ListValue;
import com.jwang261.common.valid.UpdateGroup;
import com.jwang261.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:14:42
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	@Null(message = "Save cannot be assigned id", groups = {AddGroup.class})
	@NotNull(message = "Update id must not be null", groups = {UpdateGroup.class})
	private Long brandId;
	/**
	 * $column.comments
	 */
	@NotBlank(message = "Must have name", groups = {AddGroup.class, UpdateGroup.class})//自定义提示
	private String name;
	/**
	 * $column.comments
	 */
	@URL(message = "must be a valid url", groups = {AddGroup.class, UpdateGroup.class})
	@NotEmpty(groups = {AddGroup.class})
	private String logo;
	/**
	 * $column.comments
	 */
	private String descript;
	/**
	 * $column.comments
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@ListValue(vals = {0,1}, groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * $column.comments
	 */
	@Pattern(regexp = "^[a-zA-Z]$", message = "First letter must be a letter", groups = {AddGroup.class, UpdateGroup.class})
	@NotEmpty(groups = {AddGroup.class})
	private String firstLetter;
	/**
	 * $column.comments
	 */
	@Min(value = 0, message = "Sort must larger than 0")
	@NotNull(groups = {AddGroup.class})//Integer -> NotNull, String -> NotEmpty
	private Integer sort;

}
