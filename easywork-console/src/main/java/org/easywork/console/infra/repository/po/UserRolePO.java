package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 用户角色关系实体
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRolePO extends BasePO {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 用户信息
     */
    private UserPO userPO;
    
    /**
     * 角色信息
     */
    private RolePO rolePO;
}