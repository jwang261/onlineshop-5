package com.jwang261.onlineshop.search.controller;

import com.jwang261.onlineshop.search.service.ShopSearchService;
import com.jwang261.onlineshop.search.vo.SearchParamVo;
import com.jwang261.onlineshop.search.vo.SearchResultVo;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jwang261
 * @date 2020/9/6 2:53 PM
 */
@Controller
public class SearchController {

    @Autowired
    ShopSearchService shopSearchService;



    /**
     * @param param all params
     * @return
     */
    @GetMapping("/list.html")
    public String listPage(SearchParamVo param, Model model){
        SearchResultVo result = shopSearchService.search(param);
        model.addAttribute("result", result);
        return "list";
    }

}
