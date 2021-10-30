package cn.njupt.votingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Describe: 用户实体类
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Controller
public class UserController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
