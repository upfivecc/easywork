package org.easywork.auth.trigger.http.rest;

import cn.hutool.core.util.RandomUtil;
import org.easywork.common.rest.result.Result;
import org.easywork.auth.domain.token.service.AuthTokenService;
import org.easywork.auth.infra.config.SmsAuthenticationToken;
import org.easywork.auth.trigger.http.rest.dto.AuthTokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 基于 RESTFUL的接口
 *
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/4 08:51
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class RestLoginController {

    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    /**
     * API方式登录处理
     *
     * @param body
     * @return
     */
    @PostMapping("/api/jwtLogin")
    public Result<AuthTokenDTO> jwtLogin(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return Result.success(authTokenService.generateToken(authentication));
    }

    /**
     * 发送短信验证码
     *
     * @param body
     * @return
     */
    @PostMapping("/api/smsSendCode")
    public Result<Boolean> smsSendCode(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        // 1. 生成验证码
        String code = RandomUtil.randomNumbers(6);
        log.info("sms code：{}", code);
        // 2. 缓存验证码
        // 3. 发送验证码
        return Result.success(true);
    }

    /**
     * 短信登录处理
     *
     * @param body
     * @return
     */
    @PostMapping("/api/smsLogin")
    public Result<AuthTokenDTO> smsLogin(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String code = body.get("code");
        // 1. 去除缓存中的验证码
        // 2. 验证码校验
        Authentication authentication = this.authenticationManager.authenticate(new SmsAuthenticationToken(phone, code));
        return Result.success(authTokenService.generateToken(authentication));
    }
}
