package com.jwang261.onlineshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.onlineshop.product.entity.PmsCommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 17:38:48
 */
public interface PmsCommentReplayService extends IService<PmsCommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

