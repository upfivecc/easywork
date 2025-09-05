package org.easywork.common.rest.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码
 *
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/8/20 16:29
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS("200", "成功"),

    /**
     * 未登录
     */
    NO_LOGIN("400", "未登录"),

    /**
     * 参数非法
     */
    PARAM_INVALID("401", "请求参数错误"),

    /**
     * token无效或已过期
     */
    INVALID_TOKEN("402", "token无效或已过期"),

    /**
     * 密码不正确
     */
    PASSWORD_ERROR("403", "用户名或密码不正确"),

    /**
     * 验证码不正确
     */
    VERIFY_CODE_ERROR("404", "验证码不正确"),
    /**
     * 该用户名已注册
     */
    USERNAME_ALREADY_REGISTER("405", "该用户名已注册"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST("406", "用户不存在"),

    /**
     * 请不要输入非法内容
     */
    XSS_PARAM_ERROR("407", "请求内容非法"),

    /**
     * 请不要频繁操作
     */
    REQ_LIMIT("408", "请不要频繁操作"),
    /**
     * ip白名单限制
     */
    WHITE_IP_LIMIT("409", "ip白名单限制"),

    /**
     * 系统繁忙，请稍后再试
     */
    SYSTEM_ERROR("500", "系统繁忙，请稍后再试"),

    ;


    private final String code;
    private final String msg;


}

