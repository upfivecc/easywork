package org.easywork.console.domain.model.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 10:44
 */
@Data
public class BaseEntity {

    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 逻辑删除标志 0-未删除 1-已删除
     */
    private Integer deleted;

    /**
     * 版本号（乐观锁）
     */
    private Integer version;
    /**
     * 检查部门是否启用
     */
    public boolean isDeleted() {
        return deleted != null && deleted == 1;
    }
}
