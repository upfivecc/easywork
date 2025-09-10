package org.easywork.console.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 10:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLog extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录类型 1-用户名密码 2-OAuth2 3-短信验证码
     */
    private Integer loginType;

    /**
     * 登录状态 1-成功 0-失败
     */
    private Integer status;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录信息
     */
    private String message;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登出时间
     */
    private LocalDateTime logoutTime;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 检查登录是否成功
     */
    public boolean isSuccess() {
        return status != null && status == 1;
    }

    /**
     * 是否为用户名密码登录
     */
    public boolean isPasswordLogin() {
        return loginType != null && loginType == 1;
    }

    /**
     * 是否为OAuth2登录
     */
    public boolean isOAuth2Login() {
        return loginType != null && loginType == 2;
    }

    /**
     * 是否为短信验证码登录
     */
    public boolean isSmsLogin() {
        return loginType != null && loginType == 3;
    }
}
