package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 角色实体
 *
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePO extends BasePO {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色代码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    /**
     * 数据范围 1-全部数据 2-本部门及以下数据 3-本部门数据 4-仅本人数据 5-自定义数据
     */
    private Integer dataScope;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

}