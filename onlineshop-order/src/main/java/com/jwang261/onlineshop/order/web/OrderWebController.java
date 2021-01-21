package com.jwang261.onlineshop.order.web;

import com.jwang261.onlineshop.order.service.OrderService;
import com.jwang261.onlineshop.order.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

/**
 * @author jwang261
 * @date 2021/1/15 1:01 AM
 */
@Controller
public class OrderWebController {

    @Autowired
    OrderService orderService;

    @GetMapping({"/toTrade","/toTrade.html"})
    public String toTrade(Model model) throws ExecutionException, InterruptedException {

        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("confirmData",confirmVo);
//        System.out.println(confirmVo.getItems().get(0).getImage());

        return "confirm";
    }

}
