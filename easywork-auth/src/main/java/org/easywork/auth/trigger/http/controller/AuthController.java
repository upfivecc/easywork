package org.easywork.auth.trigger.http.controller;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/8/20 16:29
 */
@Controller
public class AuthController {


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/oauth2/login")
    public String oauth2Login() {
        return "oauth2/login";
    }

    @GetMapping("/oauth2/consent")
    public String consent(@RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                          @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                          @RequestParam(OAuth2ParameterNames.STATE) String state, Model model) {
        // 可选：查询 client 信息
        model.addAttribute("clientId", clientId);
        model.addAttribute("scopes", Arrays.asList(scope.split(" ")));
        model.addAttribute("state", state);

        return "oauth2/consent";
    }

}
