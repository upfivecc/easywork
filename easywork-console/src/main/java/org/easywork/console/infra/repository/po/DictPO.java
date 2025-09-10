package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 字典实体
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_dict")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictPO extends BasePO {

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典描述
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
     * 备注
     */
    private String remark;

}