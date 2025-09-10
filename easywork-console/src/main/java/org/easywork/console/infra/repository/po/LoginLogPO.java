package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

import java.time.LocalDateTime;

/**
 * 登录日志实体
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_login_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogPO extends BasePO {

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
}