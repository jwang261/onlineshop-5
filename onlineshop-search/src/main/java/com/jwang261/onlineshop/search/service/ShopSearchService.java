package com.jwang261.onlineshop.search.service;

import com.jwang261.onlineshop.search.vo.SearchParamVo;
import com.jwang261.onlineshop.search.vo.SearchResultVo;

/**
 * @author jwang261
 * @date 2020/9/6 3:20 PM
 */
public interface ShopSearchService {
    /**
     *
     * @param param search params
     * @return search result
     */
    SearchResultVo search(SearchParamVo param);

}
