package com.jwang261.onlineshop.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.onlineshop.ware.entity.WareSkuEntity;
import com.jwang261.onlineshop.ware.vo.SkuHasStockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:55:05
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);
}

