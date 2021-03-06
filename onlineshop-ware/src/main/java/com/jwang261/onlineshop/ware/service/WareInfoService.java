package com.jwang261.onlineshop.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.onlineshop.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:55:05
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

