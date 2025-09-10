package org.easywork.console.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.domain.model.base.BaseEntity;
import org.easywork.console.infra.repository.po.DictItemPO;

import java.util.List;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 15:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Dict extends BaseEntity {

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

    /**
     * 字典项列表
     */
    private List<DictItemPO> items;

    /**
     * 检查字典是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }
}
