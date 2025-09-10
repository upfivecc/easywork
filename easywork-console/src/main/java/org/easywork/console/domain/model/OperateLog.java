package org.easywork.console.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 10:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperateLog extends BaseEntity {

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 操作状态 1-成功 0-失败
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作地点
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
     * 执行时间（毫秒）
     */
    private Long executeTime;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 检查操作是否成功
     */
    public boolean isSuccess() {
        return status != null && status == 1;
    }
}
