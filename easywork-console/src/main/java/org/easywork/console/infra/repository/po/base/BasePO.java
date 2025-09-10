package org.easywork.console.infra.repository.po.base;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 基础实体类
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BasePO {
    
    /**
     * 主键ID
     */
    @TableId
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
}