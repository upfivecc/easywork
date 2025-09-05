package org.easywork.console.trigger.http.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fiveupup
 * @version: 1.0.0
 * @date: 2025/9/2 16:59
 */
@RestController
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
