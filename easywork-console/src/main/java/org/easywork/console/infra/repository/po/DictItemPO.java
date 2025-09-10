package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 字典项实体
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_dict_item")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictItemPO extends BasePO {
    
    /**
     * 字典ID
     */
    private Long dictId;
    
    /**
     * 字典项标签
     */
    private String label;
    
    /**
     * 字典项值
     */
    private String value;
    
    /**
     * 字典项描述
     */
    private String description;
    
    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;
    
    /**
     * 排序号
     */
    private Integer sort;
    
    /**
     * 样式属性（表格回显样式）
     */
    private String cssClass;
    
    /**
     * 是否默认 1-是 0-否
     */
    private Integer isDefault;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 检查字典项是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }
    
    /**
     * 检查是否为默认项
     */
    public boolean isDefault() {
        return isDefault != null && isDefault == 1;
    }
}