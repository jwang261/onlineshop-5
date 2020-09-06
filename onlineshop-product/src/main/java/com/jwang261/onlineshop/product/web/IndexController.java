package com.jwang261.onlineshop.product.web;

import com.jwang261.onlineshop.product.entity.CategoryEntity;
import com.jwang261.onlineshop.product.service.CategoryService;
import com.jwang261.onlineshop.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author jwang261
 * @date 2020/9/1 4:00 PM
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redisson;
    @GetMapping({"/", "index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson(){
        Map<String, List<Catalog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;
    }
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        RLock lock = redisson.getLock("my-lock");

        lock.lock();
        try {
            System.out.println("枷锁");
            Thread.sleep(30000);
        }catch (Exception e){

        }finally {
            lock.unlock();
            System.out.println("释放");
        }
        return "hello";
    }
}
