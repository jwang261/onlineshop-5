package com.jwang261.onlineshop.ssoclient2.Controller;

import com.jwang261.common.constant.AuthServerConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jwang261
 * @date 2021/1/4 6:10 AM
 */
@Controller
public class HelloController {

    @Value("${sso.server.url}")
    String ssoServerUrl;

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }



    @GetMapping("/boss")
    public String employees(Model model, HttpSession session,
                            @RequestParam(value="token",required = false) String token){
        if(!StringUtils.isEmpty(token)){
            //获取真正信息
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> entity = restTemplate.getForEntity("http://sso-server.com:8080/userInfo?token=" + token, String.class);
            String body = entity.getBody();
            session.setAttribute("loginUser",body);
        }
        Object loginUser = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if(loginUser == null){
            //跳转登录服务器
            return "redirect:" + ssoServerUrl + "?redirect_url=http://client1.com:8081/employees";
        }else{
            List<String> emps = new ArrayList<>();
            emps.add("1");
            emps.add("2");
            model.addAttribute("emps",emps);
            return "list";
        }

    }


}
