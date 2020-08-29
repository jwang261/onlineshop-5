package com.jwang261.onlineshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.onlineshop.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:14:42
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

