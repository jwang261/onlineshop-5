package com.jwang261.onlineshop.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jwang261
 * @date 2021/1/15 12:25 AM
 */
@Controller
public class IndexController {

    @GetMapping("/detail.html")
    public String detail(){
        return "detail";
    }
    @GetMapping("/list.html")
    public String list(){
        return "list";
    }
    @GetMapping("/pay.html")
    public String pay(){
        return "pay";
    }
    @GetMapping("/confirm.html")
    public String confirm(){
        return "confirm";
    }

}
