package org.easywork.auth.trigger.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: fiveupup
 * @version: 1.0.0
 * @date: 2025/9/1 18:07
 */
@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping(value = "/api/hello", produces = "application/json")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
