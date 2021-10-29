package cn.njupt.votingsystem.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Describe: 用户实体类
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
public class UserController {

    @GetMapping("/index")
    public String index(HttpServletResponse response){
        String uuid = IdUtil.simpleUUID();
        response.addCookie(new Cookie("VID", uuid));
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
