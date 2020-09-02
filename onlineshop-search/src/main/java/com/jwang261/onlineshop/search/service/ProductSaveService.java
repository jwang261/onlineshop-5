package com.jwang261.onlineshop.search.service;

import com.jwang261.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author jwang261
 * @date 2020/8/31 9:04 PM
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;

}
