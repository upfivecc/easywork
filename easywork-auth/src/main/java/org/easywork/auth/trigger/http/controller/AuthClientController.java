package org.easywork.auth.trigger.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 客户端管理
 * 
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 15:02
 */
@Controller
@RequestMapping("/clients")
public class AuthClientController {
    @GetMapping("/list")
    public String list() {
        return "user/list";
    }

}
