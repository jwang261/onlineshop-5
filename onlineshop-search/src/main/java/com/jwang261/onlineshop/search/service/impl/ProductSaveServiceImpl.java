package com.jwang261.onlineshop.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.jwang261.common.to.es.SkuEsModel;
import com.jwang261.onlineshop.search.config.OnlineshopElasticSearchConfig;
import com.jwang261.onlineshop.search.constant.EsConstant;
import com.jwang261.onlineshop.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jwang261
 * @date 2020/8/31 9:05 PM
 */
@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        //将数据保存到ES中
        //1、给ES中建立索引

        //2、给ES中保存数据
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            //1、构造保存请求
            IndexRequest request = new IndexRequest(EsConstant.PRODUCT_INDEX);
            request.id(skuEsModel.getSkuId().toString());
            String s = JSON.toJSONString(skuEsModel);
            request.source(s, XContentType.JSON);
            bulkRequest.add(request);

        }


        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, OnlineshopElasticSearchConfig.COMMON_OPTIONS);
        //TODO错误处理
        boolean b = bulkResponse.hasFailures();
        List<String> collect = Arrays.stream(bulkResponse.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        if(b){
            log.info("商品上架错误{}", collect);
        }else{

            log.info("商品上架成功{}", collect);
        }

        return !b;
    }

}
