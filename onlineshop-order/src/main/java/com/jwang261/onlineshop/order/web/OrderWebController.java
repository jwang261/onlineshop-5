package com.jwang261.onlineshop.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jwang261
 * @date 2021/1/15 1:01 AM
 */
@Controller
public class OrderWebController {

    @GetMapping("/toTrade")
    public String toTrade(){
        return "confirm";
    }

}
