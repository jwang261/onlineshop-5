package com.jwang261.onlineshop.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.jwang261.common.to.es.SkuEsModel;
import com.jwang261.common.utils.Query;
import com.jwang261.onlineshop.search.config.OnlineshopElasticSearchConfig;
import com.jwang261.onlineshop.search.constant.EsConstant;
import com.jwang261.onlineshop.search.service.ShopSearchService;
import com.jwang261.onlineshop.search.vo.SearchParamVo;
import com.jwang261.onlineshop.search.vo.SearchResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jwang261
 * @date 2020/9/6 3:21 PM
 */
@Service
public class ShopSearchServiceImpl implements ShopSearchService {

    @Autowired
    RestHighLevelClient client;


    @Override
    public SearchResultVo search(SearchParamVo param) {
        //动态构建查询需要的DSL语句
        SearchResultVo result = null;
        //1、准备检索请求
        SearchRequest searchRequest = buildSearchRequest(param);


        try {
            //2、执行检索请求
            SearchResponse response = client.search(searchRequest, OnlineshopElasticSearchConfig.COMMON_OPTIONS);

            //3、封装检索结果
            result = buildSearchResult(response, param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //build result data
    //match, filter(attr, catalog, brand, price range, stock), sort, page, highlight, aggregation
    private SearchResultVo buildSearchResult(SearchResponse response, SearchParamVo param) {
        SearchResultVo result = new SearchResultVo();
        //1. return the products that matched the query
        SearchHits hits = response.getHits();
        List<SkuEsModel> esModels = new ArrayList<>();
        SearchHit[] hits1 = hits.getHits();
        if(hits.getHits() != null && hits.getHits().length > 0){
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel esModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                //set highlight
                if(!StringUtils.isEmpty(param.getKeyword())){
                    HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                    String string = skuTitle.fragments()[0].string();
                    esModel.setSkuTitle(string);
                }

                esModels.add(esModel);
            }
        }
        result.setProducts(esModels);

        //2-4 from aggregation info
        //2. All the attrs that related to the products

        List<SearchResultVo.AttrVo> attrVos = new ArrayList<>();
        ParsedNested attr_agg = response.getAggregations().get("attr_agg");
        ParsedLongTerms attr_id_agg = attr_agg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket : attr_id_agg.getBuckets()) {
            SearchResultVo.AttrVo attrVo = new SearchResultVo.AttrVo();
            //1. attrId
            long attrId = bucket.getKeyAsNumber().longValue();
            //2. attrValue TODO
            String attrName = ((ParsedStringTerms) bucket.getAggregations().get("attr_name_agg")).getBuckets().get(0).getKeyAsString();
            //3. get all the values in attr
            List<String> attrValues = ((ParsedStringTerms) bucket.getAggregations().get("attr_value_agg")).getBuckets().stream().map(item->{
                String keyAsString = ((Terms.Bucket) item).getKeyAsString();
                return keyAsString;
            }).collect(Collectors.toList());
            attrVo.setAttrId(attrId);
            attrVo.setAttrName(attrName);
            attrVo.setAttrValue(attrValues);
            attrVos.add(attrVo);
        }
        result.setAttrs(attrVos);

        //3. All the brands that related to the product
        List<SearchResultVo.BrandVo> brandVos = new ArrayList<>();
        ParsedLongTerms brand_agg = response.getAggregations().get("brand_agg");
        for (Terms.Bucket bucket : brand_agg.getBuckets()) {
            SearchResultVo.BrandVo brandVo = new SearchResultVo.BrandVo();
            //1. get brandId
            long brandId = bucket.getKeyAsNumber().longValue();
            //2. get brandName
            String brandName = ((ParsedStringTerms) bucket.getAggregations().get("brand_name_agg")).getBuckets().get(0).getKeyAsString();
            //3. get brandImg
            String brandImg = ((ParsedStringTerms) bucket.getAggregations().get("brand_img_agg")).getBuckets().get(0).getKeyAsString();
            brandVo.setBrandId(brandId);
            brandVo.setBrandName(brandName);
            brandVo.setBrandImg(brandImg);
            brandVos.add(brandVo);
        }
        result.setBrands(brandVos);

        //4. all the catalogInfo related to the product
        ParsedLongTerms catalog_agg = response.getAggregations().get("catalog_agg");
        List<SearchResultVo.CatalogVo> catalogVos = new ArrayList<>();
        List<? extends Terms.Bucket> buckets = catalog_agg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            SearchResultVo.CatalogVo catalogVo = new SearchResultVo.CatalogVo();
            //1. get catalogId
            String keyAsString = bucket.getKeyAsString();
            catalogVo.setCatalogId(Long.parseLong(keyAsString));

            //2. get catalogName
            ParsedStringTerms catalog_name_agg = bucket.getAggregations().get("catalog_name_agg");
            String catalog_name = catalog_name_agg.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(catalog_name);
            catalogVos.add(catalogVo);

        }
        result.setCatalogs(catalogVos);
        //Infos above is from agg info

        //5. page info
        result.setPageNum(param.getPageNum());
        //5.1 total rows
        long total = hits.getTotalHits().value;
        result.setTotal(total);
        //5.2 calculate pageNums
        int totalPages = (int) total % EsConstant.PRODUCT_PAGESIZE == 0 ? (int) total / EsConstant.PRODUCT_PAGESIZE : (int) total / EsConstant.PRODUCT_PAGESIZE + 1;
        result.setTotalPages(totalPages);

        List<Integer> pageNavs = new ArrayList<>();
        for(int i = 1; i <= totalPages; i++){
            pageNavs.add(i);
        }
        result.setPageNavs(pageNavs);
        return result;
    }


    private SearchRequest buildSearchRequest(SearchParamVo param) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();//build DSL
        //match, filter
        //1 Bool Query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //1.1 must
        if(!StringUtils.isEmpty(param.getKeyword())){
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
        //1.2 bool filter - catalog3Id
        if(param.getCatalog3Id() != null){
            boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
        //1.2 bool filter - brandId
        if(param.getBrandId() != null && param.getBrandId().size() > 0){
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));

        }
        //1.2 bool filter - attrs
        if(param.getAttrs() != null && param.getAttrs().size() > 0){

            //each attrStr should be a nested Query
            for (String attrStr : param.getAttrs()) {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                //attr = 1_8G:16G&2_13in:15in
                String[] s = attrStr.split("_");
                String attrId = s[0]; //1
                String[] attrValues = s[1].split(":");//[8G,16G]
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }



        }
        //1.2 bool filter - stock >>>
        //TODO 这里为了方便显示的全是无库存商品
        if(param.getHasStock() != null){
            boolQuery.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 0));

        }

        //1.2 bool filter - price range
        if(!StringUtils.isEmpty(param.getSkuPrice())){
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");

            String[] s = param.getSkuPrice().split("_");
            if(s.length == 2){
                rangeQuery.gte(s[0]).lte(s[1]);
            }else if(s.length == 1){
                //_500
                if(param.getSkuPrice().startsWith("_")){
                    rangeQuery.lte(s[0]);
                }
                if(param.getSkuPrice().endsWith("_")){
                    rangeQuery.gte(s[0]);
                }
            }

            boolQuery.filter(rangeQuery);
        }

        sourceBuilder.query(boolQuery);

        //2 sort, page, highlight
        //2.1 sort
        if(!StringUtils.isEmpty(param.getSort())){
            String sort = param.getSort(); // sort = hotScore_asc/desc
            String[] s = sort.split("_");
            SortOrder order = s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0], order);
        }
        //2.2 pages
        // pageNum:3 from:10 size:5 -> from = (pageNum - 1) * size
        sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);
        //2.3 highlight
        if(!StringUtils.isEmpty(param.getKeyword())){
            HighlightBuilder builder = new HighlightBuilder();
            builder.field("skuTitle");
            builder.preTags("<b style='color:red'>");
            builder.postTags("</b>");
            sourceBuilder.highlighter(builder);
        }

        //3 aggregation
        //3.1 Brand agg
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);
        //品牌聚合子聚合
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brand_agg);
        //3.2 Catalog agg
        TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        sourceBuilder.aggregation(catalog_agg);
        //3.3 Attr agg
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        //聚合出所有attrId
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        //attrId 对应的name
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        //all possible attr Values
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));

        attr_agg.subAggregation(attr_id_agg);
        sourceBuilder.aggregation(attr_agg);

        String s = sourceBuilder.toString();
        System.out.println("DSL: " + s);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder );
        return searchRequest;
    }
}
